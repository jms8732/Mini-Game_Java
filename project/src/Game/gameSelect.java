package Game;

import javax.swing.*;
import java.awt.*;

public class gameSelect extends JPanel {
    public JButton omok, mineSearch;
    private myLIstener ml;
    gameSelect(){
        ml = new myLIstener();
        //이미지 첨부
        ImageIcon i1= new ImageIcon("omok.png");
        getImage(i1);
        omok = new JButton(i1); //오목
        omok.addActionListener(ml); //리스너 추가
        omok.setBorderPainted(false);
        omok.setContentAreaFilled(false);
        this.add(omok);
        ImageIcon i2=  new ImageIcon("mine.png");
        getImage(i2);
        mineSearch = new JButton(i2);
        mineSearch.addActionListener(ml); //리스너 추가
        mineSearch.setBorderPainted(false);
        mineSearch.setContentAreaFilled(false);
        this.add(mineSearch);
    }
    private void getImage(ImageIcon ic){
        Image i = ic.getImage();
        Image i1 = i.getScaledInstance(150,150,Image.SCALE_DEFAULT);
        ic.setImage(i1);
    }
}
