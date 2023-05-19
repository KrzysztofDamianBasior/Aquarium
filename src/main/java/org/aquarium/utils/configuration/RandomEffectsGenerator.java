package org.aquarium.utils.configuration;

public class RandomEffectsGenerator {
    final private String[] musicFilesPathnames = {
            "src/main/resources/assets/musicFiles/[NoCopyrightMusic]LastSummer-Ikson.WAV",
            "src/main/resources/assets/musicFiles/[NoCopyrightMusic]Island-MBB.WAV",
            "src/main/resources/assets/musicFiles/[NoCopyrightMusic]HappyLife-FREDJI.WAV",
            "src/main/resources/assets/musicFiles/[NoCopyrightMusic]ColdSun-Del.WAV"
    };
    final private String[] backgroundFilesPathnames = {
            "src/main/resources/assets/backgrounds/gameBackground1.jpg",
            "src/main/resources/assets/backgrounds/gameBackground2.jpg",
            "src/main/resources/assets/backgrounds/gameBackground3.jpg",
            "src/main/resources/assets/backgrounds/gameBackground4.jpg",
    };

    private double draw(double min, double max) {
        assert min <= max : "draw(min, max): min > max?";
        return min + Math.random() * (max - min);
    }

    private int draw(int min, int max) {
        assert min <= max : "draw(min, max): min > max?";
        return min + (int) (Math.random() * (1L + max - min));
    }

    private String draw(String... strings) {
        return strings[(int) (Math.random() * strings.length)];
    }

    public String drawMusicFilePathname() {
        return draw(musicFilesPathnames);
    }

    public String drawBackgroundFilePathname() {
        return draw(backgroundFilesPathnames);
    }

    public int drawLevelDurationTimeValue() {
        return draw(30, 60);
    }
}