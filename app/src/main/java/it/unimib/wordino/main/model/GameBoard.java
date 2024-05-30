package it.unimib.wordino.main.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GameBoard {

    private String[][] gameboard;

    public void changeValue(int i, int j, String value){
        this.gameboard[i][j] = value;
    }
    public String getValue(int i, int j){
        return this.gameboard[i][j];
    }

    public void addColor(int i, int j, String color){
        changeValue(i, j, getValue(i, j) + color);
    }

    public GameBoard(){
        this.gameboard = new String[6][5];
    }


    public void reset(){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                gameboard[i][j] = null;
            }
        }
    }

    public GameBoard(String[][] gameboard) {
        this.gameboard = gameboard;
    }

}
