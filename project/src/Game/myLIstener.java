package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Omok.insertPlayer;
import Omok.omokScoreBoard;
import mineSearch.mineSize;
import mineSearch.mineScoreBoard;

public class myLIstener implements ActionListener {
    insertPlayer ip;

    myLIstener() {
        ip = new insertPlayer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton tmp = (JButton) e.getSource();
        if (tmp.getIcon() != null) {
            if (tmp.getIcon().toString().equals("omok.png")) { //이미지
                //오목 수행
                ip.setVisible(true);
                ip.setEmpty(); //입력할 텍스트 상자를 빈칸으로 하기 위한 메소드
                omokScoreBoard.getInstance().panelRemove(); //점수판을 새롭게 만들기 위해 전의 컴포넌트를 삭제
                GameSetting.getInstance().setVisible(false);
            } else if (tmp.getIcon().toString().equals("mine.png")) {
                //지뢰 찾기 수행
                //mineSize.getInstance().setVisible(true);
                mineSize ms = new mineSize();
                ms.setVisible(true);
                mineScoreBoard.getInstance().panelRemove();
                GameSetting.getInstance().setVisible(false);
            }
        } else if (tmp.getText().equals("오목 스코어")) {
            //오목 스코어 판 보여주는 창
            omokScoreBoard.getInstance().setVisible(true);
            GameSetting.getInstance().setVisible(false);
        } else if (tmp.getText().equals("지뢰 스코어")) {
            //지뢰 스코어 판 보여주는 창
            mineScoreBoard.getInstance().setVisible(true);
            GameSetting.getInstance().setVisible(false);
        } else {
            //종료하기
            System.exit(0);
        }
    }
}
