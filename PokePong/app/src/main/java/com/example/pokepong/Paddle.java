package com.example.pokepong;

public class Paddle {
    int x1, y1;
    int x2, y2;
    int speed = 20;

    public Paddle(int startX, int startY, int width, int height) {
        // (left top)
        this.x1 = startX;           // left
        this.y1 = startY;           // top

        // (right bottom)
        this.x2 = this.x1 + width;      // right
        this.y2 = this.y1 + height;     // bottom
    }

}
