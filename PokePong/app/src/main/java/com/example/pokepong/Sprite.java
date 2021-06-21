package com.example.pokepong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

//@TODO: The "OOP" version of sprites
public class Sprite {
    private int x;
    private int y;
    private int size = 20;
    private int direction;
    private int speed;

    public Sprite(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public Sprite(int x, int y, int size, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}


