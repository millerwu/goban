package com.example.wu.gobang.game;

/**
 * Created by intel on 2016/4/13.
 */
public class GameJudgeLogic {

    GameJudgeLogic()
    {

    }

    static public boolean isGameEnd(int map[][], Coordinate last)
    {
        int left, right, i, j;
        int chessflag = map[last.x][last.y];
        //"|"
        for(i = last.y-1, left = 0; i >= 0 && chessflag == map[last.x][i]; i--, left++);
        for(j = last.y+1, right = 0; j < Game.SCALE_MEDIUM && chessflag == map[last.x][j]; j++, right++);
        if (left+right >= 4)
        {
            return true;
        }
        //"--"
        for (i = last.x-1, left = 0; i >= 0 && chessflag == map[i][last.y]; i--, left++);
        for (j = last.x+1, right = 0; j < Game.SCALE_MEDIUM && chessflag == map[j][last.y]; j++, right++);
        if (left + right >= 4)
        {
            return true;
        }
        // "/"
        for (i = last.x-1, j = last.y+1, left = 0; i >= 0 && j < Game.SCALE_MEDIUM && chessflag == map[i][j]; i--, j++, left++);
        for (i = last.x+1, j = last.y-1, right = 0; i < Game.SCALE_MEDIUM && j >= 0 && chessflag == map[i][j]; i++, j--, right++);
        if ( left + right >= 4)
        {
            return true;
        }
        // "\"
        for (i = last.x-1, j = last.y-1, left = 0; i >= 0 && j >= 0 && chessflag == map[i][j]; i--, j--, left++);
        for (i = last.x+1, j = last.y+1, right = 0; i < Game.SCALE_MEDIUM && j < Game.SCALE_MEDIUM && chessflag == map[i][j]; i++, j++, right++);
        if (left + right >= 4)
        {
            return true;
        }
        return false;
    }

    static public int getLive4(int map[][], Coordinate last)
    {
        int count = 0;
        int left, right, i, j;
        int chessflag = map[last.x][last.y];
        //"|"
        for(i = last.y-1, left = 0; i >= 0 && chessflag == map[last.x][i]; i--, left++);
        for(j = last.y+1, right = 0; j < Game.SCALE_MEDIUM && chessflag == map[last.x][j]; j++, right++);
        if (left+right == 3 && i > 0 && map[last.x][i]== Game.SPACE && j < Game.SCALE_MEDIUM && map[last.x][j] == Game.SPACE)
        {
            count++;
        }
        //" -- "
        for (i = last.x-1, left = 0; i >= 0 && chessflag == map[i][last.y]; i--, left++);
        for (j = last.x+1, right = 0; j < Game.SCALE_MEDIUM && chessflag == map[j][last.y]; j++, right++);
        if (left + right == 3 && i > 0 && map[i][last.y]==Game.SPACE && j < Game.SCALE_MEDIUM && map[j][last.y] == Game.SPACE)
        {
            count++;
        }
        //"/"
        for (i = last.x-1, j = last.y+1, left = 0; i >= 0 && j < Game.SCALE_MEDIUM && chessflag == map[i][j]; i--, j++, left++);
        for (i = last.x+1, j = last.y-1, right = 0; i < Game.SCALE_MEDIUM && j >= 0 && chessflag == map[i][j]; i++, j--, right++);
        if ( left + right == 3 && (last.x-left-1)> 0 && (last.y+left+1)<Game.SCALE_MEDIUM && map[last.x-left-1][last.y+left+1]==Game.SPACE &&
                (last.x+right+1) < Game.SCALE_MEDIUM && (last.y-right-1)>0 && map[last.x+right+1][last.y-right-1] == Game.SPACE)
        {
            count++;
        }
        // "\"
        for (i = last.x-1, j = last.y-1, left = 0; i >= 0 && j >= 0 && chessflag == map[i][j]; i--, j--, left++);
        for (i = last.x+1, j = last.y+1, right = 0; i < Game.SCALE_MEDIUM && j < Game.SCALE_MEDIUM && chessflag == map[i][j]; i++, j++, right++);
        if (left + right == 3 && (last.x-left-1)>0 && (last.y-left-1)>0 && map[last.x-left-1][last.y-left-1]==Game.SPACE && (last.x+right+1)<Game.SCALE_MEDIUM
                && (last.y+right+1)<Game.SCALE_MEDIUM && map[last.x+right+1][last.y+right+1]==Game.SPACE)
        {
            count++;
        }
        return count;
    }
    static public int getDied4(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getLive3(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getDied3(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getLive2(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getDied2(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getLive1(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

    static public int getDied1(int map[][], Coordinate last)
    {
        int count = 0;
        return count;
    }

}
