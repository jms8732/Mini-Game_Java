package Omok;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myDialog extends JDialog implements ActionListener {
    private JButton retry, showScore;
    private JLabel label;
    private JPanel panel;
    private char[][] board;
    private omok o= null;
    private String name;
    private int totalCost;
    myDialog(String s, char[][] board,int total){
        setLocationRelativeTo(null);
        if(o == null)
            o = omok.getInstance();
        this.board = board;
        this.totalCost = total; //몇수에 이겼는지
        this.name = s; //유저 이름
        setSize(200,100);
        setTitle("Victory");
        this.setLayout(new FlowLayout());
        panel =new JPanel();
        label =new JLabel(s + "님 축하합니다.");
        retry = new JButton("다시 하기");
        retry.addActionListener(this);
        showScore = new JButton("점수 보기");
        showScore.addActionListener(this);
        panel.add(retry);
        panel.add(showScore);
        setResizable(false);
        this.add(label,BorderLayout.NORTH);
        this.add(panel,BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton tmp = (JButton)e.getSource();
        if(tmp == retry){
            //다시하기 버튼, 배열을 초기화 시킨다.
            for(int i = 0; i < board.length ; i++){
                for(int j= 0 ; j < board[i].length ; j++){
                    board[i][j] = '\0';
                }
            }
            this.setVisible(false);
            o.setVisible(true); //초기화된 판을 다시 보여준다.
            o.changeColor();
            omokBoard.getInstance().totalCount = 0;
        }
        else {
            //점수 보기 버튼
            omokScoreBoard.getInstance().addScore(this.name,this.totalCost); //점수판에 추가
            omokScoreBoard.getInstance().setSocreBoard(); //점수판을 보여주기 위해서 메소드 수행
            omokScoreBoard.getInstance().setVisible(true);
            this.setVisible(false);
            o.setVisible(false);
        }
    }
}
