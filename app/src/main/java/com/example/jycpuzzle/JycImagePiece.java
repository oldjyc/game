package com.example.jycpuzzle;

import android.graphics.Bitmap;

public class JycImagePiece {

        private int index;
        private Bitmap bitmap;

        public JycImagePiece() {

        }

        public JycImagePiece(int index, Bitmap bitmap) {
            this.index = index;
            this.bitmap = bitmap;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

    }
