package Game;

import javax.swing.*;
import java.awt.*;

public class gameScore extends JPanel {
    JButton oScore, mScore; //오목 스코어, 지뢰 스코어
    myLIstener ml;
    gameScore(){
        ml = new myLIstener();
        //이미지 첨부
        oScore = new JButton("오목 스코어");
        oScore.setFont(new Font("Serif", Font.ITALIC,15));
        oScore.addActionListener(ml); //리스너 추가
        JLabel label = new JLabel("                       ");
        this.add(oScore);
        this.add(label);
        mScore = new JButton("지뢰 스코어");
        mScore.setFont(new Font("Serif",Font.ITALIC,15));
        mScore.addActionListener(ml); //리스너 추가
        this.add(mScore);
    }
}
