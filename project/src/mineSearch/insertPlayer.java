package mineSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class insertPlayer extends JDialog implements ActionListener {
    private JTextField name1; //이름을 입력하는 textfield
    private JButton confirm;
    private JPanel panel1, panel2, panel3;
    private JLabel user1;
    private static insertPlayer ip = null;
    private int mapSize, time ; //맵 크기와 시간
    public static insertPlayer getInstance(){
        if(ip == null)
            ip = new insertPlayer();
        return ip;
    }
    private insertPlayer() {
        setSize(300, 200);
        setLocationRelativeTo(null);
        this.setTitle("승리");
        panel1 = new JPanel(); //유저 이름과 버튼을 부착하는 패널1
        panel2 = new JPanel(); //유저 이름과 버튼을 부착하는 패널2
        panel3 = new JPanel(); //버튼을 담당하는 패널
        user1 = new JLabel("Player1 : ");
        confirm = new JButton("확인");
        confirm.addActionListener(this);
        name1 = new JTextField(10);
        panel1.add(user1);
        panel1.add(name1);
        panel3.add(confirm);
        add(panel1);
        add(panel3,BorderLayout.SOUTH);
        pack();
    }
    public void setEmpty(){
        name1.setText("");
    }
    public void setMapTime(int mapSize, int time){
        this.mapSize = mapSize;
        this.time = time;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton tmp = (JButton) e.getSource();
        if (confirm == tmp) {
            //확인을 눌렀을 시
            String tmp1 = name1.getText(); //입력된 스트링을 받아온다.
            //특수문자와 공백은 허용안된다.
            if (!tmp1.isEmpty()) {
                if (!tmp1.matches("[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*"))
                    JOptionPane.showMessageDialog(null, "특수문자 이름은 안됩니다!!", "Message", JOptionPane.ERROR_MESSAGE);
                else {
                    //이름이 정확하게 입력이 됐을 시에 수행, 지뢰 점수판을 보여준다.
                    mineScoreBoard.getInstance().addScore(tmp1,this.mapSize,this.time);
                    mineScoreBoard.getInstance().setSocreBoard();
                    mineScoreBoard.getInstance().setVisible(true);
                    this.setVisible(false);
                }
            } else {
                //플레이어 이름을 입력하지 않았을 경우 팝업창을 띄운다.
                JOptionPane.showMessageDialog(null, "플레이어 이름을 입력해 주세요 !!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
