package com.github.mlee0967.minesweeper.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.github.mlee0967.minesweeper.R;
import com.github.mlee0967.minesweeper.game.Game;

public class Cell extends View implements View.OnClickListener , View.OnLongClickListener {
    public Cell(Context context, int row, int col) {
        super(context);
        flagged = false;
        revealed = false;
        mine = false;
        clicked = false;
        val = 0;
        this.row = row;
        this.col = col;

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public int getVal() {
        return val;
    }

    public void incVal() {
        ++val;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public boolean isMine(){
        return mine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setClicked() {
        this.clicked = true;
        invalidate();
    }

    public void setFlagged(boolean flagged){
        this.flagged = flagged;
        invalidate();
    }

    public void setMine(){
        this.mine = true;
    }

    public void setRevealed() {
        this.revealed = true;
        invalidate();
    }

    @Override
    public void onClick(View v) {
        Game.getInstance().click(row, col);
    }

    @Override
    public boolean onLongClick(View v) {
        Game.getInstance().flag(row, col);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(flagged){
            if(Game.getInstance().isGameOver() && !mine)
                drawMineCrossed(canvas);
            else
                drawFlag(canvas);
        }else if(clicked){
            if(mine)
                drawMineExploded(canvas);
            else
                drawNumber(canvas);
        }else if(revealed){
            if(mine)
                drawMine(canvas);
            else
                drawNumber(canvas);
        }else
            drawDefaultCell(canvas);
    }

    private void drawDefaultCell(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.default_cell);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flagged);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawMine(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.mine);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawMineCrossed(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.mine_crossed);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawMineExploded(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.mine_exploded);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawables[val]);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private static int[] drawables = { R.drawable.num_0, R.drawable.num_1, R.drawable.num_2, R.drawable.num_3,
            R.drawable.num_4, R.drawable.num_5, R.drawable.num_6, R.drawable.num_7, R.drawable.num_8 };
    private int row;
    private int col;
    private int val; //number of bombs adjacent to this cell
    private boolean mine;
    private boolean flagged;
    private boolean revealed;
    private boolean clicked;
}