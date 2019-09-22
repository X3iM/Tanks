package com.study.game.level;

import com.study.Utils.Utils;
import com.study.game.Game;
import com.study.game.Player;
import com.study.graphics.TextureAtlas;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Level {

    public static final int         TILE_SCALE          = 8;
    public static final int         TILE_IN_GAME_SCALE  = 2;
    public static final int         SCALED_TILE_SIZE    = TILE_IN_GAME_SCALE * TILE_SCALE;
    public static final int         TILES_IN_WIDTH      = Game.WEIGHT / SCALED_TILE_SIZE;
    public static final int         TILES_IN_HEIGHT     = Game.HEIGHT / SCALED_TILE_SIZE;

    private final int                       timeOfWaterAnimation = (int)(Player.speed * 500);
    private long                            time;
    private static boolean                  isChange = false;
    private static Map<TileType, Tile>      tiles;
    private static Integer[][]              tileMap;
    private List<Point>                     grassCords;

    public Level(TextureAtlas atlas) {
        time = new Date().getTime();

        tileMap = new Integer[TILES_IN_WIDTH][TILES_IN_HEIGHT];
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.BRICK, new Tile(atlas.cut(32 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.BRICK));
        tiles.put(TileType.METAL, new Tile(atlas.cut(32 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.METAL));
        tiles.put(TileType.WATER_FIRST, new Tile(atlas.cut(34 * TILE_SCALE, 6 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.WATER_FIRST));
        tiles.put(TileType.WATER_SECOND, new Tile(atlas.cut(32 * TILE_SCALE, 6 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.WATER_SECOND));
        tiles.put(TileType.GRASS, new Tile(atlas.cut(34 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.GRASS));
        tiles.put(TileType.EMPTY, new Tile(atlas.cut(36 * TILE_SCALE, 6 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.EMPTY));
        tiles.put(TileType.UP_LEFT_EAGLE, new Tile(atlas.cut(38 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.UP_LEFT_EAGLE));
        tiles.put(TileType.UP_RIGHT_EAGLE, new Tile(atlas.cut(39 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.UP_RIGHT_EAGLE));
        tiles.put(TileType.DOWN_LEFT_EAGLE, new Tile(atlas.cut(38 * TILE_SCALE, 5 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.DOWN_LEFT_EAGLE));
        tiles.put(TileType.DOWN_RIGHT_EAGLE,
                new Tile(atlas.cut(39 * TILE_SCALE, 5 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TILE_IN_GAME_SCALE,
                        TileType.DOWN_RIGHT_EAGLE));
        tiles.put(TileType.UP_LEFT_DEAD_EAGLE,
                new Tile(atlas.cut(40 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TILE_IN_GAME_SCALE,
                        TileType.UP_LEFT_DEAD_EAGLE));
        tiles.put(TileType.UP_RIGHT_DEAD_EAGLE,
                new Tile(atlas.cut(41 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TILE_IN_GAME_SCALE,
                        TileType.UP_RIGHT_DEAD_EAGLE));
        tiles.put(TileType.DOWN_LEFT_DEAD_EAGLE,
                new Tile(atlas.cut(40 * TILE_SCALE, 5 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TILE_IN_GAME_SCALE,
                        TileType.DOWN_LEFT_DEAD_EAGLE));
        tiles.put(TileType.DOWN_RIGHT_DEAD_EAGLE,
                new Tile(atlas.cut(41 * TILE_SCALE, 5 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TILE_IN_GAME_SCALE,
                        TileType.DOWN_RIGHT_DEAD_EAGLE));
        tiles.put(TileType.ICE, new Tile(atlas.cut(37 * TILE_SCALE, 5 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.ICE));

        tileMap = Utils.levelParse("res/level");
        grassCords = new ArrayList<Point>();
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                if (tile.type() == TileType.GRASS) {
                    grassCords.add(new Point(j * SCALED_TILE_SIZE, i * SCALED_TILE_SIZE));
                }
            }
        }

        /*tileMap[10][10] = TileType.BRICK.numeric();
        tileMap[11][11] = TileType.WATER.numeric();
        tileMap[12][12] = TileType.METAL.numeric();
        tileMap[13][13] = TileType.GRASS.numeric();
        tileMap[14][14] = TileType.ICE.numeric();
         */
    }

    public void update() {
        if (isChange) {
            grassCords = new ArrayList<Point>();
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[i].length; j++) {
                    Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                    if (tile.type() == TileType.GRASS) {
                        grassCords.add(new Point(j * SCALED_TILE_SIZE, i * SCALED_TILE_SIZE));
                    }
                }
            }
            isChange = false;
        }
    }

    public void render(Graphics2D g) {
        //boolean isChange = false;
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                if (tile.type() == TileType.WATER_SECOND || tile.type() == TileType.WATER_FIRST) {
                    Water water = new Water(i, j);
                    water.start();
                }
                else if (tile.type() != TileType.GRASS) {
                    tile.render(g, j * SCALED_TILE_SIZE, i * SCALED_TILE_SIZE);
                }
            }
        }
        //if (isChange)
        //    time = new Date().getTime();
    }

    public void renderGrass(Graphics2D g) {
        for (Point p : grassCords) {
            tiles.get(TileType.GRASS).render(g, p.x, p.y);
        }
    }

    public static Integer[][] getTileMap() {
        return tileMap;
    }
}
