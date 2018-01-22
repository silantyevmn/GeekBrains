package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import java.io.Serializable;
import java.util.HashMap;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class Map implements Serializable {
    private static final char SYMB_GRASS = 'g';
    private static final char SYMB_SKY = 's';

    private static final int CELL_SIZE_PX = 40;

    private transient HashMap<Character, TextureRegion> groundMap;
    private int length;
    private int height;
    private int endOfWorldX;
    private char[][] data;
    private BaseUnit[][] blockMask;

    public int getEndOfWorldX() {
        return endOfWorldX;
    }

    public Map(int length) {
        this.length = length;
        this.height = 50;
        this.endOfWorldX = length * CELL_SIZE_PX;
        this.data = new char[length][height];
        this.blockMask = new BaseUnit[length][height];
        this.afterLoad();
    }

    public void afterLoad() {
        groundMap = new HashMap<Character, TextureRegion>();
        TextureRegion[] grounds=Assets.getInstance().getAtlas().findRegion("ground").split(128, 128)[0];
        //0-право,1-вверху,2-влево,3 умолчание
        groundMap.put('5', grounds[3]);
        groundMap.put('7', grounds[2]);
        groundMap.put('8', grounds[1]);
        groundMap.put('9', grounds[0]);
    }

    public void fillGroundPart(int x1, int x2, int groundHeight) {
        if (x2 > length - 1) x2 = length - 1;
        for (int i = x1; i <= x2; i++) {
            for (int j = 0; j < groundHeight; j++) {
                data[i][j] = SYMB_GRASS;
            }
        }
    }
    private int indexFrameGround(int x, int y, int maxX, int maxY) {
        //0-право,1-вверху,2-влево,3 умолчание
        int indexFrame=3;
        if(y+1<maxY && data[x][y+1]!=SYMB_GRASS){
            indexFrame=1;
            if(x-1>0 && data[x-1][y]!=SYMB_GRASS){
                indexFrame=2;
            }
            if(x+1<maxX && data[x+1][y]!=SYMB_GRASS){
                indexFrame=0;
            }
        }
        return indexFrame;
    }

    public void repackGround() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (data[i][j] == SYMB_GRASS) {
                    data[i][j] = '5';
                    try {
                        if (data[i][j + 1] == 's') {
                            data[i][j] = '8';
                        }
                        if (data[i][j + 1] == 's' && data[i + 1][j] == 's') {
                            data[i][j] = '9';
                        }
                        if (data[i][j + 1] == 's' && data[i - 1][j] == 's') {
                            data[i][j] = '7';
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public void generateMap() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = SYMB_SKY;
            }
        }
        int height = 4;
        int position = 0;
        fillGroundPart(0, 3, height);
        position = 4;
        while (position < length) {
            int len = MathUtils.random(3, 5);
            height += MathUtils.random(-2, 2);
            if (height < 1) height = 1;
            if (height > 6) height = 6;
            fillGroundPart(position, position + len - 1, height);
            position += len;
        }
        for (int i = 0; i < 0; i++) {
            int island_length = MathUtils.random(3, 8);
            int island_position = MathUtils.random(10, length - island_length - 4);
            int island_y = MathUtils.random(0, this.height - 10);
            if (!checkFutureIsle(island_position, island_y, island_length)) {
                i--;
                continue;
            }
            for (int j = 0; j < island_length; j++) {
                data[island_position + j][island_y] = SYMB_GRASS;
            }
        }
        repackGround();
    }

    public boolean checkFutureIsle(int positionX, int positionY, int isleLength) {
        try {
            for (int i = positionX - 2; i <= positionX + isleLength + 2; i++) {
                for (int j = positionY - 2; j <= positionY + 2; j++) {
                    if (data[i][j] != SYMB_SKY) return false;
                }
            }
            for (int i = positionX - 3; i <= positionX - 1; i++) {
                if (data[i][positionY - 3] != SYMB_SKY) return true;
            }
            for (int i = positionX + isleLength + 1; i <= positionX + isleLength + 3; i++) {
                if (data[i][positionY - 3] != SYMB_SKY) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (data[i][j] != SYMB_SKY) {
                    batch.draw(groundMap.get(data[i][j]), i * CELL_SIZE_PX, j * CELL_SIZE_PX,CELL_SIZE_PX,CELL_SIZE_PX);
                }
            }
        }
    }

    private boolean isCellEmpty(int cellX, int cellY) {
        if (data[cellX][cellY] != SYMB_SKY) {
            return false;
        }
        return true;
    }

    private boolean isCellEmpty(BaseUnit unit, int cellX, int cellY) {
        if (data[cellX][cellY] != SYMB_SKY || (blockMask[cellX][cellY] != null && blockMask[cellX][cellY] != unit)) {
            return false;
        }
        return true;
    }

    public boolean checkSpaceIsEmpty(float x, float y) {
        if (x < 0 || x > endOfWorldX) return false;
        int cellX = (int) (x / CELL_SIZE_PX);
        int cellY = (int) (y / CELL_SIZE_PX);
        return isCellEmpty(cellX, cellY);
    }

    public boolean checkSpaceIsEmpty(BaseUnit unit, float x, float y) {
        if (x < 0 || x > endOfWorldX) return false;
        int cellX = (int) (x / CELL_SIZE_PX);
        int cellY = (int) (y / CELL_SIZE_PX);
        return isCellEmpty(unit, cellX, cellY);
    }

    public void blockCell(BaseUnit unit) {
        if (unit.getCenterX() < 0 || unit.getCenterX() > endOfWorldX) return;
        int cellX = (int) (unit.getCenterX() / CELL_SIZE_PX);
        int cellY = (int) (unit.getCenterY() / CELL_SIZE_PX);
        blockMask[cellX][cellY] = unit;
    }

    public void update(float dt) {
        for (int i = 0; i < blockMask.length; i++) {
            for (int j = 0; j < blockMask[0].length; j++) {
                blockMask[i][j] = null;
            }
        }
    }
}
