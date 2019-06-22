package Omok;

import org.omg.CORBA.OBJ_ADAPTER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class omok extends JFrame implements ActionListener {
    private JLabel user1, user2, time1;
    private final int TIME = 10; //시간 설정
    private JPanel panel1, panel2;
    private JButton btn1; //무르기 버튼
    private omokInit oi; //오목을 처음 시작할때 호출되는 클래스
    private String u1, u2; //이름을 받는 스트링
    private omokBoard ob; //바둑판을 담당하는 패널
    private int x, y; //바둑판의 크기를 담당하는 변수
    private boolean check; //턴을 넘기기 위해서 사용되는 변수
    public static omok o = null;
    private HashMap<Boolean, String> userTable;
    private Timer timer;
    private JLabel label3; //돌의 색깔을 알려주는
    public static omok getInstance() {
        if (o == null) {
            o = new omok();
            return o;
        } else
            return o;

    }
    //초기에 오목 생성자
    private omok() {
        //유저 이름을 등록
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //화면 중앙에 위치하기 위해
        this.setTitle("오목게임");
        user1 = new JLabel("");
        user1.setFont(new Font("Serif", Font.ITALIC,20));
        user2 = new JLabel("");
        user2.setFont(new Font("Serif",Font.ITALIC,20));
        JLabel vs = new JLabel(" VS ");
        vs.setFont((new Font("Serif",Font.BOLD,25)));
        panel1 = new JPanel();
        //패널1(이름 vs 이름)을 위에 부착
        panel1.add(user1);
        panel1.add(vs);
        panel1.add(user2);
        panel1.setBackground(Color.ORANGE);
        panel2 = new JPanel();
        JLabel label = new JLabel("Time : " );
        label.setFont(new Font("Serif",Font.BOLD,omokBoard.circleSize));
        time1 = new JLabel(Integer.toString(TIME));
        time1.setFont(new Font("Serif", Font.ITALIC,omokBoard.circleSize));
        btn1 = new JButton(" 무르기");
        label3 = new JLabel("     ");
        label3.setOpaque(true);
        label.setBackground(Color.BLACK);
        btn1.addActionListener(this);
        panel2.add(label);
        panel2.add(time1);
        panel2.add(btn1);
        panel2.add(label3);
        panel2.setBackground(Color.ORANGE);
        this.add(panel1, BorderLayout.NORTH); // 이름과 vs를 가지고 있는 패널 부착
        this.add(panel2, BorderLayout.SOUTH); //시간과 무르기 버튼을 가지고 있는 패널 부착
        pack(); //크기에 맞춰서 화면 바뀜
    }
    public void setBoard(omokBoard ob) {
        this.ob = ob;
        if(ob.getParent() == null)
            this.add(ob, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(x * omokBoard.circleSize + 7, y * omokBoard.circleSize + 120)); //화면 조정
        //check = ob.getTurn();
        this.setResizable(false); //임의로 크기 변경 불가
        this.setVisible(true);
        Iterator it = userTable.keySet().iterator();
        check  = (boolean)it.next();
        ob.setTurn(check);
        if(check)
            user1.setForeground(Color.BLACK);
        else
            user1.setForeground(Color.WHITE);

        u1= userTable.get(check);
        user1.setText(u1);
        if(!check)
            user2.setForeground(Color.BLACK);
        else
            user2.setForeground(Color.WHITE);
        u2 = userTable.get(!check);
        user2.setText(u2);
        /*for(Map.Entry<Boolean,String> m : userTable.entrySet()){
            if(m.getKey()) {
                user1.setForeground(Color.BLACK);
            }
            else
                user2.setForeground(Color.WHITE);
        }*/
        label3.setForeground(ob.getColor());
        //타이머는 interval하는 순간에 계속해서 발생한다.
        setTimer(); //타이머 시작
        repaint();
        pack();
    }
    public void changeColor(){
        label3.setBackground(ob.getColor());
    }
    //타이머를 설정하는 메소드
    private void setTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //시간을 변경하는 리스너
                String tmp = time1.getText();
                int ti = Integer.parseInt(tmp);
                ti -= 1;
                if (ti < 0) {
                    check = ob.getTurn(); //턴을 얻어온다
                    ob.setTurn(!check); //시간이 초과되어서 턴이 넘어간다.
                    label3.setBackground(ob.getColor());
                    repaint();
                    timer.stop(); //타이머 멈춤
                    initTime(); //시간 초기화
                    timer.restart();
                } else {
                    tmp = Integer.toString(ti);
                    time1.setText(tmp);
                }
            }
        });
        timer.start(); //타이머 시작
    }
    //타이머를 얻는 메소드
    public Timer getTimer() {
        return timer;
    }
    //시간 초기화
    public void initTime() {
        time1.setText(Integer.toString(TIME));
    }
    //동전 던지기 및 바둑판 선택하는 메소드
    public void init(String u1, String u2) {
        this.u1 = u1;
        this.u2 = u2;
        oi = new omokInit(u1, u2);
        oi.setPreferredSize(new Dimension(500, 500));
        oi.setVisible(true);
    }
    //이름을 설정하는 메소드
    public void setUser(String u1, String u2) {
        this.u1 = u1;
        this.u2 = u2;
        this.user1.setText(u1);
        this.user2.setText(u2);
    }
    //유저 테이블을 설정하는 메소드
    public void setUserTable(HashMap<Boolean, String> u) {
        this.userTable = u;
    }
    //바둑판의 크기를 정하는 메소드
    public void setSize(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public HashMap<Boolean, String> getUserTable() {
        return this.userTable;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //무르기 버튼을 눌렀을 때 발생하는 이벤트
        check = ob.getTurn(); //턴을 얻어옴
        label3.setBackground(ob.getColor());
        ob.setTurn(!check); //턴을 저장
        repaint();
        pack();
        initTime();
        timer.restart();
    }
}
