package Game;

import javax.swing.*;
import java.awt.*;

public class GameSetting extends JFrame {
    private gameSelect gSelect; //게임 선택기
    private JButton exit; //종료하기 버튼
    private gameScore gScore; //스코어를 보여주는 패널
    private static GameSetting gs; //gameSetting을 한개를 만들고 계속 이용하기 위해서 static으로 처리
    private myLIstener ml;
    private JPanel panel;
    public static GameSetting getInstance(){
        if(gs != null)
            return gs;
        else {
            gs = new GameSetting();
            return gs;
        }
    }

    private  GameSetting(){
        panel = new JPanel();
        setLocationRelativeTo(null);
        ml = new myLIstener();
        gSelect = new gameSelect();
        gScore = new gameScore();
        exit = new JButton("종료하기");
        exit.addActionListener(ml);
        panel.add(exit);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setTitle("게임시작하기");
        this.add(gSelect,BorderLayout.CENTER); //게임 선택기 추가
        this.add(gScore, BorderLayout.NORTH); //스코어를 보여주는 패널 추가
        this.add(panel, BorderLayout.SOUTH); //밑에 종료하기 버튼 추가
        pack();
        this.setResizable(false); //임의로 사이즈 변경 불가
        this.setVisible(true);
    }
}

