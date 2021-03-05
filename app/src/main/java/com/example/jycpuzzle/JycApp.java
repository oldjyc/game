package com.example.jycpuzzle;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;

public class JycApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        picturePoint = new HashMap<Integer, Integer>();
        picturePoint.put(0, com.example.jycpuzzle.R.drawable.p0);

        textPoint = new HashMap<Integer, Integer>();
        textPoint.put(0, com.example.jycpuzzle.R.drawable.b0);


        sentencePoint= new HashMap<Integer, Integer>();
        sentencePoint.put(0, com.example.jycpuzzle.R.drawable.s0);

        guessPoint= new HashMap<Integer, Integer>();
        guessPoint.put(0, com.example.jycpuzzle.R.drawable.g0);

    }
    private boolean pFlag=false;

    private Map<Integer, Integer> guessPoint;
    Map<Integer, Integer> textPoint;
    private Map<Integer, Integer> picturePoint;
    private Map<Integer, Integer> sentencePoint;
    private int puzzlePiece=3;

    String name="jyc";
    String picturePath;
    String birthDay;
    int point=0;
    int time=0;
    String score="0";
    int puzzlePoint=0;
    boolean isDoubleGame=false;

    public void setpFlag(boolean pFlag) {
        this.pFlag = pFlag;
    }

    public boolean ispFlag() {
        return pFlag;
    }

    public Map<Integer, Integer> getGuessPoint() {
        return guessPoint;
    }

    public void setGuessPoint(Map<Integer, Integer> guessPoint) {
        this.guessPoint = guessPoint;
    }

    public int getPuzzlePiece() {
        return puzzlePiece;
    }

    public void setPuzzlePiece(int puzzlePiece) {
        this.puzzlePiece = puzzlePiece;
    }

    public Map<Integer, Integer> getSentencePoint() {
        return sentencePoint;
    }

    public void setSentencePoint(Map<Integer, Integer> sentencePoint) {
        this.sentencePoint = sentencePoint;
    }

    public boolean isDoubleGame() {
        return isDoubleGame;
    }

    public void setDoubleGame(boolean doubleGame) {
        isDoubleGame = doubleGame;
    }

    public Map<Integer, Integer> getTextPoint() {
        return textPoint;
    }

    public void setTextPoint(Map<Integer, Integer> textPoint) {
        this.textPoint = textPoint;
    }

    public Map<Integer, Integer> getPicturePoint() {
        return picturePoint;
    }

    public void setPicturePoint(Map<Integer, Integer> picturePoint) {
        this.picturePoint = picturePoint;
    }

    public int getPuzzlePoint() {
        return puzzlePoint;
    }

    public void setPuzzlePoint(int puzzlePoint) {
        this.puzzlePoint = puzzlePoint;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }




}
