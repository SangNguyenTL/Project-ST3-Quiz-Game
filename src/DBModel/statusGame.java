/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DBModel;

/**
 *
 * @author nhats
 */
public class statusGame {
    private Player player;
    private int levelQuestion;
    private boolean changeQuest;
    private boolean per50;
    private int timeTotal;
    private int money;
    private int oldMoney;
    private int oldTime;

    public statusGame(Player player, int levelQuestion, boolean changeQuest, boolean per50, int timeTotal, int money) {
        this.player = player;
        this.levelQuestion = levelQuestion;
        
        this.changeQuest = changeQuest;
        this.per50 = per50;
        this.timeTotal = timeTotal;
        this.money = money;
        this.oldMoney = player.getMoney();
        this.oldTime = player.getTotalTime();
    }
    public statusGame() {
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevelQuestion() {
        return levelQuestion;
    }

    public boolean isChangeQuest() {
        return changeQuest;
    }

    public boolean isPer50() {
        return per50;
    }

    public int getTimeTotal() {
        return timeTotal;
    }

    public int getMoney() {
        return money;
    }

    public int getOldMoney() {
        return oldMoney;
    }

    public int getOldTime() {
        return oldTime;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLevelQuestion(int levelQuestion) {
        this.levelQuestion = levelQuestion;
    }

    public void setChangeQuest(boolean changeQuest) {
        this.changeQuest = changeQuest;
    }

    public void setPer50(boolean per50) {
        this.per50 = per50;
    }

    public void setTimeTotal(int timeTotal) {
        this.timeTotal = timeTotal;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setOldMoney(int oldMoney) {
        this.oldMoney = oldMoney;
    }

    public void setOldTime(int oldTime) {
        this.oldTime = oldTime;
    }
    
}
