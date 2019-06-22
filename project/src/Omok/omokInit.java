package Omok;

import com.sun.org.apache.xml.internal.resolver.helpers.BootstrapResolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Random;

public class omokInit extends JFrame implements ActionListener {
    private JLabel user1, user2; //사용자 이름을 입력받는 라벨
    private JCheckBox check1, check2; //앞 뒤를 선택하기 위한 check
    private JPanel panel1, panel2, panel3;
    private ImageIcon icon1, icon2; //동전 이미지 추가
    private JButton confirm; //확인 버튼
    private Random rand;
    private ImageIcon front, back;
    private omok o;
    private setBoard sb;
    private HashMap<Boolean, String> userTable;

    omokInit(String u1, String u2) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("공수 선택");
        setSize(600, 600);
        rand = new Random();
        setLocationRelativeTo(null);
        //그림 첨부
        user1 = new JLabel();
        JLabel label = new JLabel("Player 1 : ");
        label.setFont(new Font("Serif",Font.BOLD,25));
        JLabel label1 = new JLabel("         Player 2 : " );
        label1.setFont(new Font("Serif",Font.BOLD,25));
        user1.setText(u1);
        user1.setFont(new Font("Serif", Font.ITALIC, 25));
        user2 = new JLabel();
        user2.setText(u2);
        user2.setFont(new Font("Serif", Font.ITALIC, 25));
        check1 = new JCheckBox();
        check1.setText("앞");
        check1.setFont(new Font("Serif", Font.BOLD, 25));
        check2 = new JCheckBox();
        check2.setText("뒤");
        check2.setFont(new Font("Serif", Font.BOLD, 25));
        panel1 = new JPanel(); //유저 이름을 가지고있는 패널
        panel1.add(label);
        panel1.add(user1);
        panel1.add(label1);
        panel1.add(user2);
        panel2 = new JPanel(); //이미지를 가지고 있는 패널
        front = new ImageIcon("front.png");
        getImage(front);
        back = new ImageIcon("back.png");
        getImage(back);
        JLabel l1 = new JLabel();
        l1.setIcon(front);
        JLabel l2 = new JLabel();
        l2.setIcon(back);
        panel2.add(l1);
        panel2.add(l2);
        panel3 = new JPanel(); //선택을 가지고 있는 패널
        JPanel p = new JPanel();
        JLabel label2= new JLabel("                     ");
        p.add(check1);
        p.add(label2);
        p.add(check2);
        panel2.add(p, BorderLayout.SOUTH);
        confirm = new JButton("확인");
        confirm.addActionListener(this);

        panel3.add(confirm, BorderLayout.SOUTH);
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        this.add(panel3, BorderLayout.SOUTH);
    }
    //이미지를 지뢰 이미지
    private void getImage(ImageIcon ic) {
        Image i = ic.getImage();
        Image i1 = i.getScaledInstance(250, 400, Image.SCALE_DEFAULT);
        ic.setImage(i1);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //버튼을 누르면 수행
        if (check1.isSelected() && check2.isSelected()) {
            //둘다 선택이 됐으면 랜덤으로 선공 선택
            int tmp = rand.nextInt(10);
            if (o == null)
                o = omok.getInstance();
            //유저를 테이블에 등록
            userTable = new HashMap<>();
            boolean c1 = false;
            boolean c2 = false;
            if (tmp <= 5)
                c1 = false;
            else
                c1 = true;
            userTable.put(c1, user1.getText());
            c2 = !(c1);
            userTable.put(c2, user2.getText());
            o.setUserTable(userTable);
            this.setVisible(false);
            sb = new setBoard(o);
        } else
            JOptionPane.showMessageDialog(null, "둘다 선택 해주세요", "공수 선택 오류", JOptionPane.ERROR_MESSAGE);
    }
}
