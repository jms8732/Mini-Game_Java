package mineSearch;

import Game.GameSetting;
import Omok.omokScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class mineScoreBoard extends JFrame implements ActionListener {
    private JPanel panel, panel1,panel2;
    private JButton home;
    private static Vector<Vector<String>> v;
    private JLabel label1,label2,label3;
    private static mineScoreBoard msb = null;
    public static mineScoreBoard getInstance(){
        if(msb == null) {
             msb = new mineScoreBoard();
            return msb;
        }else
            return msb;

    }
    private mineScoreBoard(){
        v = new Vector<>();
        setLocationRelativeTo(null);
        setTitle("지뢰찾기 점수판");
        setResizable(false);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1 = new JLabel("이름");
        label1.setFont(new Font("Serif", Font.BOLD,15));
        label2= new JLabel("맵 크기");
        label2.setFont(new Font("Serif", Font.BOLD,15));
        label3 = new JLabel("클리어 시간");
        label3.setFont(new Font("Serif", Font.BOLD,15));
        panel = new JPanel(); //이름과 총 수를 담당하는 패널
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel1= new JPanel(); //등록된 점수판를 담당하는 패널
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS)); //수직으로 표현하기위해
        panel2 = new JPanel(); //버튼을 담당하는 패널
        home = new JButton("처음으로");
        home.addActionListener(this);
        panel2.add(home);
        this.add(panel,BorderLayout.NORTH);
        this.add(panel2, BorderLayout.SOUTH);
        pack();
    }
    //점수판에 점수 추가
    public void addScore(String name,int mapSize, int time){
        //벡터에 값을 넣는다.
        Vector<String> t = new Vector<>();
        t.add(name);
        t.add(Integer.toString(mapSize));
        t.add(Integer.toString(time));
        v.add(t); //최종적인 벡터에 값을 넣는다.
    }
    public void setSocreBoard(){
        sort();
        for(int i = 0; i< v.size(); i++){
            JPanel p = new JPanel();
            JLabel l1 = new JLabel();
            JLabel l2 = new JLabel();
            JLabel l3= new JLabel();
            //이름과 총 수를 등록
            l1.setText(v.get(i).get(0));
            l1.setFont(new Font("Serif",Font.ITALIC,15));
            l2.setText(v.get(i).get(1) + "x" + v.get(i).get(1));
            l2.setFont(new Font("Serif",Font.ITALIC,15));
            l3.setText(v.get(i).get(2) + "초");
            l3.setFont(new Font("Serif",Font.ITALIC,15));
            p.add(l1);
            p.add(l2);
            p.add(l3);
            panel1.add(p);
        }
        this.add(panel1,BorderLayout.CENTER);
        pack();
    }
    //정렬 메소드
    private void sort() {
        int i , j;
        for (i = 0; i < v.size(); i++) {
            int small = Integer.parseInt(v.get(i).get(2)); //벡터의 스트링을 정수형으로
            int smallidx =i;
            for (j = i + 1; j < v.size(); j++) {
                int compare = Integer.parseInt(v.get(j).get(2)); //비교대상
                if (small > compare) {
                    smallidx = j;
                    //swap
                    Vector<String> tmp = v.get(i);
                    Vector<String> tmp1 = v.get(smallidx);
                    v.remove(i);
                    v.add(i,tmp1); //정렬
                    v.remove(smallidx);
                    v.add(smallidx,tmp);
                }
            }
        }
    }
    //패널을 지우는 메소드, 수직으로 할 경우, 지워야하는 컴포넌트 idx 수정해야됨
    public void panelRemove(){
        panel1.removeAll();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton h = (JButton)e.getSource();
        //처음 버튼이 눌러졌을때
        if(h == home){
            GameSetting.getInstance().setVisible(true);
            this.setVisible(false);
        }
    }
}

