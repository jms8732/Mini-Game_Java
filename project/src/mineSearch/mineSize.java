package mineSearch;

import Omok.omok;
import Omok.omokBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class mineSize extends JFrame implements ItemListener {
    private JRadioButton rbn1, rbn2, rbn3, rbn4,rbn5;
    private ButtonGroup bgp;
    private int size; //지뢰게임 사이즈
    public  mineSize() {
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3, 2));
        this.setSize(300, 300);
        this.setTitle("지뢰게임 크기");
        setResizable(false);
        this.setVisible(true);
        init();
    }
    private void init() {
        rbn1 = new JRadioButton("10 x 10");
        rbn1.setFont(new Font("Serif",Font.BOLD,20));
        rbn1.setVerticalTextPosition(SwingConstants.CENTER);
        rbn1.setHorizontalAlignment(SwingConstants.CENTER);
        rbn1.addItemListener(this);
        rbn2 = new JRadioButton("20 x 20");
        rbn2.setFont(new Font("Serif",Font.BOLD,20));
        rbn2.setVerticalTextPosition(SwingConstants.CENTER);
        rbn2.setHorizontalAlignment(SwingConstants.CENTER);
        rbn2.addItemListener(this);
        rbn3 = new JRadioButton("30 x 30");
        rbn3.setFont(new Font("Serif",Font.BOLD,20));
        rbn3.setVerticalTextPosition(SwingConstants.CENTER);
        rbn3.setHorizontalAlignment(SwingConstants.CENTER);
        rbn3.addItemListener(this);
        rbn4 = new JRadioButton("40 x 40");
        rbn4.setFont(new Font("Serif",Font.BOLD,20));
        rbn4.setVerticalTextPosition(SwingConstants.CENTER);
        rbn4.setHorizontalAlignment(SwingConstants.CENTER);
        rbn4.addItemListener(this);
        rbn5 = new JRadioButton("50 x 50");
        rbn5.setFont(new Font("Serif",Font.BOLD,20));
        rbn5.setVerticalTextPosition(SwingConstants.CENTER);
        rbn5.setHorizontalAlignment(SwingConstants.CENTER);
        rbn5.addItemListener(this);
        bgp = new ButtonGroup();
        //버튼 그룹에 추가
        bgp.add(rbn1);
        bgp.add(rbn2);
        bgp.add(rbn3);
        bgp.add(rbn4);
        bgp.add(rbn5);
        //프레임에 추가
        this.add(rbn1);
        this.add(rbn2);
        this.add(rbn3);
        this.add(rbn4);
        this.add(rbn5);
    }
    private void setSize(JRadioButton btn) {
        String tmp = btn.getActionCommand();
        int x = Integer.parseInt(tmp.substring(0, 2));
        this.size = x;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        //라디오 버튼이 눌렸을 시
        JRadioButton tmp = (JRadioButton) e.getItem();
        setSize(tmp);
        mineCount mc = new mineCount();
        mc.setMineSize(this.size);
        mc.setVisible(true);
        tmp.setSelected(false);
        this.setVisible(false);
    }
}
