package mineSearch;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

//지뢰찾기 메인 화면
public class mineSearch extends JFrame implements ActionListener {
    JButton[][] buttons; //버튼들
    JLabel[][] labels; //라벨들
    char[][] mineMap; //지뢰찾기에 사용되는 이차원 배열
    public static mineSearch mss = null;
    private JPanel panel1, panel2;
    private int size, mineCount, initCount; //initCount는 맨 처음 등록된 지뢰 개수
    private JButton reset;
    private JLabel label1, label2;
    private JLabel label3,label4;
    private Timer timer;
    private ArrayList<coordi> openList, closeList;
    private int btnSize = 20;
    private myMouse mm; //마우스 이벤트 객체
    private int correct; //지뢰찾은 개수
    private boolean mc;
    private  Color[] colors;
    private ImageIcon flag,question, smile,sad;
    public static mineSearch getInstance() {
        if (mss == null)
            mss = new mineSearch();
        return mss;
    }
    public mineSearch() {
        setTitle("지뢰찾기");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        flag = new ImageIcon("flag.png");
        question = new ImageIcon("question.png");
        smile = new ImageIcon("smile.png");
        sad = new ImageIcon("sad.png");
        label3 = new JLabel("Count : " );
        label3.setFont(new Font("Serif",0,btnSize-7));
        label4 = new JLabel("Time : " );
        label4.setFont(new Font("Serif",0,btnSize-7));
        setColor(); //색깔
        pack();
    }
    //색깔 설정
    private void setColor(){
        colors = new Color[8];
        colors[0] = Color.CYAN;
        colors[1] = Color.MAGENTA;
        colors[2] = Color.GREEN;
        colors[3] = Color.ORANGE;
        colors[4] = Color.YELLOW;
        colors[5] = Color.BLUE;
        colors[6] = Color.LIGHT_GRAY;
        colors[7] = Color.PINK;
    }
    //기본적인 설정을 하기 위한 메소드
    public void settings(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.initCount = mineCount;
        panel1 = new JPanel();
        mc= false;
        this.correct = 0; //깃발을 세운 곳에 정확하게 맞는가를 확인하는 count
        label1 = new JLabel(Integer.toString(mineCount));
        label1.setFont(new Font("Serif",Font.ITALIC,btnSize-7));
        label2 = new JLabel("0");
        label2.setFont(new Font("Serif",Font.ITALIC,btnSize-7));
        reset = new JButton();
        reset.setBorderPainted(false);
        getImage(smile);
        reset.setIcon(smile);
        reset.addActionListener(this);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = label2.getText();
                int ti = Integer.parseInt(tmp);
                ti += 1; //시간을 계속해서 증가시킨다.
                tmp = Integer.toString(ti);
                label2.setText(tmp);
            }
        });
        timer.start(); //타이머 시작
        panel1.add(label3); //지뢰 개수
        panel1.add(label1);
        panel1.add(reset);
        panel1.add(label4); //시간
        panel1.add(label2);
        this.add(panel1, BorderLayout.NORTH);
        panel2 = new JPanel();
        panel2.setLayout(null); //절대 배치 관리자
        mm = new myMouse();
        init();
    }
    //지뢰찾기 초기화함수
    private void init() {
        mineMap = new char[size][size];
        buttons = new JButton[size][size];
        labels = new JLabel[size][size];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton(); //버튼 객체 생성
                buttons[i][j].addActionListener(this); //각각의 버튼에 리스너 부착
                buttons[i][j].addMouseListener(mm); //마우스 이벤트 리스너 부착
                buttons[i][j].setVerticalAlignment(SwingConstants.CENTER);
                buttons[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                buttons[i][j].setBounds(btnSize * j, btnSize * i, btnSize, btnSize);
                labels[i][j] = new JLabel("0"); //라벨들을 0으로 다 초기화
                labels[i][j].setVerticalAlignment(SwingConstants.CENTER);
                labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                panel2.add(buttons[i][j]);
            }
        }
        initMineMap();
        this.add(panel2, BorderLayout.CENTER);
        this.setSize(size * btnSize + 6, size * btnSize + 73);
    }
    //지뢰찾기 맵 초기화
    private void initMineMap() {
        Random rand = new Random();
        //지뢰 개수 만큼 반복문 수행
        for (int i = 0; i < mineCount; i++) {
            int randX = rand.nextInt(this.size);
            int randY = rand.nextInt(this.size);
            if (mineMap[randX][randY] != 'M') {
                mineMap[randX][randY] = 'M'; //랜덤으로 생성한 좌표에 해당 지뢰를 추가
                labels[randX][randY].setText("M");
            } else
                i--; //중복된 위치에 지뢰좌표가 나오는 경우
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (mineMap[i][j] != 'M')
                    mineMap[i][j] = '0'; //0으로 초기화
            }
        }
        //지뢰를 다 생성 한 후 3x3의 윈도우를 통해서 지뢰 개수 파악
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (mineMap[i][j] == 'M') //지뢰를 기준으로 3x3 마스킹을 수행한다.
                    masking(i - 1, j - 1); //3x3의 맨 왼쪽위부터 시작
            }
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(mineMap[i][j] + " ");
            }
            System.out.println();
        }
    }
    //3x3 마스킹
    private void masking(int x, int y) {
        int count = 1; //무조껀 지뢰가 있는 좌표이기 때문에 count는 1이다
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (0 <= x + i && x + i < this.size && y + j < this.size && y + j >= 0) { //범위내에 존재하면
                    if (mineMap[x + i][y + j] != 'M') {
                        count += Character.digit(mineMap[x + i][y + j], 10); //누적으로 하기 위해서
                        mineMap[x + i][y + j] = Character.forDigit(count, 10); //10진수로 숫자 넣는다.
                        labels[x + i][y + j].setText(Integer.toString(count));
                        count = 1;
                    }
                }
            }
        }
    }
    //패널 및 openlist, closelist를 모두 초기화하는 메소드
    private void reSetPanel() {
        panel2.removeAll(); //패널 2에 붙어있는 모든 컴포넌트를 제거
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[j][i].setText("");
                labels[i][j].setIcon(null); //라벨에 있는 그림 제거
                buttons[j][i].setIcon(null); //버튼에 붙어있는 그림 제거
                panel2.add(buttons[j][i]); //버튼을 다시 부착
            }
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                mineMap[i][j] = '0';
            }
        }
        mc = false;
        openList.clear();
        closeList.clear();
        repaint();
    }
    //타이머를 재작동
    private void restart() {
        label2.setText("0");
        label1.setText(Integer.toString(initCount));
        correct = 0;
        mineCount = initCount; //초기값 다시 부여
        timer.restart();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //얼굴 버튼과 나머지 지뢰찾기 버튼
        JButton tmp = (JButton) e.getSource();
        if (tmp == reset) {
            reset.setIcon(smile);//얼굴 표정이 바뀌어야됨
            restart();
            reSetPanel();
            initMineMap();
        } else {
            int y = tmp.getX() / btnSize;
            int x = tmp.getY() / btnSize;
            if (mineMap[x][y] == 'M') {
                mineFind(x,y); //지뢰를 눌렀을 경우
                timer.stop();
                mc = true;
                getImage(sad);
                reset.setIcon(sad);
            } else if(!mc){ //그외 나머지 눌렀을 경우
                broaden(x, y);
                System.out.println();
                for (int i = 0; i < labels.length; i++) {
                    for (int j = 0; j < labels[i].length; j++) {
                        System.out.print(labels[i][j].getText() + " ");
                    }
                    System.out.println();
                }
            }
        }
    }
    //openlist와 closelist에서 정당성 검사
    private boolean checkList(int x, int y) {//오픈 리스트에서 비교
        boolean checkO = true;
        boolean checkC = true;
        for (coordi cd : openList) {
            int tmpX = cd.getX();
            int tmpY = cd.getY();
            if (tmpX == x && tmpY == y) {
                checkO = false;
                break; //오픈 리스트에 해당 좌표가 존재
            }
        }
        for (coordi cd : closeList) {
            int tmpX = cd.getX();
            int tmpY = cd.getY();
            if (tmpX == x && tmpY == y) {
                checkC = false;
                break;
            }
        }
        if (checkC && checkO)
            return true;
        else
            return false;
    }
    //지뢰를 찾게 될 경우 수행되는 메소드
    private void mineFind(int x, int y) {
        ImageIcon ic = new ImageIcon("mine.png");
        ImageIcon cc = new ImageIcon("selectMine.png");
        ImageIcon nm = new ImageIcon("notMine.png");
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (mineMap[i][j] == 'M') {
                    if(x == i && y == j){
                        Image ii = cc.getImage();
                        Image i1 = ii.getScaledInstance(btnSize,btnSize,Image.SCALE_DEFAULT);
                        cc.setImage(i1);
                        labels[i][j].setIcon(cc); //선택된 부분에 지뢰가 있을 경우
                    }
                    else{
                        //그외의 경우
                        getImage(ic);
                        labels[i][j].setIcon(ic);
                    }
                    panel2.remove(buttons[i][j]); //버튼 제거
                    labels[i][j].setText("");
                    labels[i][j].setVerticalAlignment(SwingConstants.CENTER);
                    labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    BevelBorder bb = new BevelBorder(BevelBorder.LOWERED); //안으로 들어간 모양의 경계선
                    labels[i][j].setBorder(bb);
                    labels[i][j].setBounds(btnSize * j, btnSize * i, btnSize, btnSize);
                    panel2.add(labels[i][j], (i * this.size) + j);
                    repaint();
                }
                else{
                    //지뢰가 없는 곳에 깃발이 있는 경우
                    if(buttons[i][j].getIcon() != null && buttons[i][j].getIcon().toString().equals("flag.png")){
                        getImage(nm);
                        labels[i][j].setIcon(nm);
                    }
                    panel2.remove(buttons[i][j]); //버튼 제거
                    labels[i][j].setText("");
                    labels[i][j].setVerticalAlignment(SwingConstants.CENTER);
                    labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    BevelBorder bb = new BevelBorder(BevelBorder.LOWERED); //안으로 들어간 모양의 경계선
                    labels[i][j].setBorder(bb);
                    labels[i][j].setBounds(btnSize * j, btnSize * i, btnSize, btnSize);
                    panel2.add(labels[i][j], (i * this.size) + j);
                    repaint();
                }

            }
        }
    }
    //이미지를 지뢰 이미지
    private void getImage(ImageIcon ic){
        Image i = ic.getImage();
        Image i1 = i.getScaledInstance(btnSize,btnSize,Image.SCALE_DEFAULT);
        ic.setImage(i1);
    }
    //그외 나머지 메소드
    private void broaden(int x, int y) {
        boolean checkO = true; //오픈리스트에서사용
        boolean checkC = true; //close리스트에서 사용
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED); //안으로 들어간 모양의 경계선
        coordi c;
        if (checkList(x, y)) //오픈 리스트와 close 리스트에 존재하지 않을 경우
        {
            c = new coordi();
            c.setXY(x, y);
            openList.add(c);
        }

        while (openList.size() > 0) { //그림 그리기
            coordi cd = openList.get(0);
           if(mineMap[cd.getX()][cd.getY()] == '0') { //비어있는 칸만 수행
                int tmpX = cd.getX() - 1;
                int tmpY = cd.getY() - 1;
                maskingSearch(tmpX, tmpY);
            }
            openList.remove(0); //오픈 리스트에서 없애고
            closeList.add(cd); //close 리스트에 추가한다.
        }

        //버튼을 라벨로 바꾸는 반복문
        for (coordi cd : closeList) {
            int tmpX = cd.getX();
            int tmpY = cd.getY();
            if (buttons[tmpX][tmpY].getText().equals("!") || buttons[tmpX][tmpY].getText().equals("?")) { //이미지로 바꿀때 수정해야됨
                mineCount++;
                buttons[tmpX][tmpY].setIcon(null);
                label1.setText(Integer.toString(mineCount));
            }
            panel2.remove(buttons[tmpX][tmpY]);
            if (mineMap[tmpX][tmpY] == '0')
                labels[tmpX][tmpY].setText(" "); //0
            else {
                labels[tmpX][tmpY].setText(Character.toString(mineMap[tmpX][tmpY])); //0을 제외한 나머지 숫자
                labels[tmpX][tmpY].setForeground(colors[Character.digit(mineMap[tmpX][tmpY],10)]); //해당 숫자
            }
            labels[tmpX][tmpY].setBorder(bb);
            labels[tmpX][tmpY].setBounds(btnSize * tmpY, btnSize * tmpX, btnSize, btnSize);
            panel2.add(labels[tmpX][tmpY], (tmpX * this.size) + tmpY);
            repaint();
        }

    }
    //빈칸의 좌표를 넣기 위한 openlist에 넣기 위한 메소드
    private void maskingSearch(int x, int y) {
        boolean check = false;
        coordi c;
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED); //안으로 들어간 모양의 경계선
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (0 <= x + i && x + i < this.size && y + j < this.size && y + j >= 0) { //범위내에 존재하면
                    if (mineMap[x + i][y + j] != 'M') {
                        check = checkList(x + i, y + j); //중복 여부검사, '0'인 값만 추가
                        if (check) { //둘다 없을 경우
                            c = new coordi();
                            c.setXY(x + i, y + j);
                            openList.add(c);
                        }
                    }
                }
            }
        }
    }
    //마우스 이벤트를 얻기 위한 클래스
    class myMouse extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            //x 좌표와 y 좌표
            int tmpX = e.getX() / btnSize;
            int tmpY = ((e.getY() - panel1.getHeight()) / btnSize) - 1;
            System.out.println(" x : " + tmpX + " y : " + tmpY);
            if (e.isMetaDown()) {
                if (e.getSource() instanceof JButton) //버튼이 눌렀을 경우
                {
                    isMine(e.getSource());
                    checkMine(e.getSource()); //깃발인 상태에서 모든 지뢰를 찾았을 경우
                    if (correct == initCount) {
                        //승리
                        int t = Integer.parseInt(label2.getText());
                        deleteAll();
                        insertPlayer.getInstance().setEmpty();
                        insertPlayer.getInstance().setMapTime(size, t);
                        label2 = null;
                        insertPlayer.getInstance().setVisible(true);
                        mineSearch.getInstance().setVisible(false);
                        correct = 0;
                    }
                }
            }
        }
    }
    //모든 컴포넌트를 지우는 메소드
    private void deleteAll() {
        for(Component c : this.getContentPane().getComponents())
            this.getContentPane().remove(c);
        panel1 = null;
        panel2 = null;
        buttons = null;
        labels = null;
        mineMap = null;
        label1 = null;
        label2 = null;
        openList.clear();
        closeList.clear();
    }
    //지뢰있는가를 판별하는 메소드
    private void checkMine(Object e) {
        JButton btn = (JButton) e;
        if (btn.getIcon() != null && btn.getIcon().toString().equals("flag.png")) {
            int tmpX = btn.getX() / btnSize;
            int tmpY = btn.getY() / btnSize;
            if (mineMap[tmpY][tmpX] == 'M')
                correct++;
        }
    }
    //마우스 우클릭을 했을 경우 실행되는 메소드
    private void isMine(Object e) {
        JButton btn = (JButton) e;
        Image i;
        String t = btn.getText();
        if (btn.getIcon() == null) { //이미지로 바꿀때 수정해야  된다
            getImage(flag);
            btn.setIcon(flag);
            mineCount--;
            label1.setText(Integer.toString(mineCount));
        } else if (btn.getIcon().toString().equals("flag.png")) {
            mineCount++;
            getImage(question);
            btn.setIcon(question);
            if (mineMap[btn.getY() / btnSize][btn.getX() / btnSize] == 'M')
                correct--;
            label1.setText(Integer.toString(mineCount));
        } else if(btn.getIcon().toString().equals("question.png")){
            btn.setIcon(null);
        }
        repaint();
    }
}
