package com.example.jycpuzzle;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class JycSplitterUtil {

    //传递bitmap切成piece*piece块，返回List<imagepiece>

    //图片切割
    public static List<JycImagePiece> splitImage(Bitmap bitmap, int piece) {

        //作为返回值传递
        List<JycImagePiece> Jyc_ImagePieces = new ArrayList<>();

        //获取图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //根据宽高取最小值达到正方形(可以简单处理近似正方形图片)
        int pieceWidth = Math.min(width, height) / piece;

        //进行切割
        for (int i = 0; i < piece; i++) {
            for (int j = 0; j < piece; j++) {
                JycImagePiece Jyc_ImagePiece = new JycImagePiece();
                Jyc_ImagePiece.setIndex(j + i * piece);

                int x = j * pieceWidth;
                int y = i * pieceWidth;

                //第一次循环为0,0,
                Jyc_ImagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));
                //返回保存到list的值
                Jyc_ImagePieces.add(Jyc_ImagePiece);
            }
        }
        return Jyc_ImagePieces;
    }



}
