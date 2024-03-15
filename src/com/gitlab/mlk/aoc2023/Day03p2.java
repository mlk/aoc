import com.gitlab.mlk.aoc2023.Point;

import java.util.*;

static String example = """
        """.trim();

StringBuffer currentNumber = new StringBuffer();

Map<Point, List<Integer>> parts = new HashMap<>();
Point currentGear = null;
void main(String... arg){
    String[] lines = example.split("\n");


    for(int x = 0; x < lines.length; x++) {
        for(int y = 0; y<lines[x].length(); y++) {
            char cur = at(lines, x, y);
            if(Character.isDigit(cur)) {
                currentNumber.append(cur);
                for(int xi = x -1; xi<=x+1; xi++) {
                    for(int yi = y -1; yi<=y+1; yi++) {
                        char outside = at(lines, xi, yi);

                        if(outside == '*') {
                            currentGear = new Point(xi, yi);
                        }
                    }
                }
            } else {
                check();
            }
        }
        check();
    }
    System.out.println(parts.values().stream().filter(x -> x.size() == 2).mapToInt(x -> x.get(0) * x.get(1)).sum());
}

void check() {
    if (!currentNumber.isEmpty()) {

        if (currentGear != null) {
            parts.compute(currentGear, (_, oldValue) -> {
                var x = Optional.ofNullable(oldValue).orElse(new ArrayList<>());
                x.add(Integer.parseInt(currentNumber.toString()));
                return x;
            });
        }
        currentNumber = new StringBuffer();
        currentGear = null;
    }
}

char at(String[] lines, int x, int y) {
    if(x<0 || x>= lines.length || y < 0 || y >= lines[x].length()) return '.';
    return lines[x].charAt(y);
}