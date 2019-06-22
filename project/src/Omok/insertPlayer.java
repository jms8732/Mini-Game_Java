package Omok;

import Game.GameSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class insertPlayer extends JDialog implements ActionListener {
    private JTextField name1, name2; //이름을 입력하는 textfield
    private JButton confirm, cancel; //확인과 취소
    private JPanel panel1, panel2, panel3;
    private JLabel user1, user2;
    private omok o;
    public insertPlayer() {
        setSize(200, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("이름 입력");
        panel1 = new JPanel(); //유저 이름과 버튼을 부착하는 패널1
        panel2 = new JPanel(); //유저 이름과 버튼을 부착하는 패널2
        panel3 = new JPanel(); //버튼을 담당하는 패널
        user1 = new JLabel("Player1 : ");
        user2 = new JLabel("Player2 : ");
        setLayout(new FlowLayout());
        confirm = new JButton("시작하기");
        confirm.addActionListener(this);
        cancel = new JButton("뒤로가기");
        cancel.addActionListener(this);
        name1 = new JTextField(10);
        name2 = new JTextField(10);
        panel1.add(user1);
        panel1.add(name1);
        panel2.add(user2);
        panel2.add(name2);
        panel3.add(confirm);
        panel3.add(cancel);
        add(panel1);
        add(panel2);
        add(panel3);
    }
    public void setEmpty(){
        name1.setText("");
        name2.setText("");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton tmp = (JButton) e.getSource();
        if (confirm == tmp) {
            //확인을 눌렀을 시
            String tmp1 = name1.getText(); //입력된 스트링을 받아온다.
            String tmp2 = name2.getText();
            //특수문자와 공백은 허용안된다.
            if (!tmp1.isEmpty() && !tmp2.isEmpty()) {
                if (!tmp1.matches("[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*") || !tmp2.matches("[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*"))
                    JOptionPane.showMessageDialog(null, "특수문자 이름은 안됩니다!!", "Message", JOptionPane.ERROR_MESSAGE);
                else {
                    //이름이 정확하게 입력이 됐을 시에 수행
                    o = omok.getInstance();
                    //o.setUser(tmp1,tmp2); //이름 설정
                    this.setVisible(false);
                    o.init(tmp1,tmp2);
                }
            } else {
                //플레이어 이름을 입력하지 않았을 경우 팝업창을 띄운다.
                JOptionPane.showMessageDialog(null, "플레이어 이름을 입력해 주세요 !!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //취소를 눌렀을 시
            int result = JOptionPane.showConfirmDialog(null, "뒤로 돌아가시겠습니까?", "돌아가기", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //확인을 눌렀을 시
                name1.setText(""); //유저이름을 작성하는 칸을 비운다
                name2.setText("");
                this.setVisible(false);
                GameSetting.getInstance().setVisible(true);
            }
        }
    }
}
