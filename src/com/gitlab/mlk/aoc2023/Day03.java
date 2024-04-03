import java.util.ArrayList;
import java.util.List;

static String example = """

        """.trim();

StringBuffer currentNumber = new StringBuffer();
boolean found = false;
List<String> parts = new ArrayList<>();

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
                        // System.out.println(cur + " -- " + xi + "x" +yi + "=" + outside);

                        if(!Character.isDigit(outside) && outside != '.') {
                            found = true;
                        }
                    }
                }
            } else {
                check();
            }
        }
        check();
    }
    System.out.println(parts.stream().mapToInt(Integer::parseInt).sum());
}

void check() {
    if(!currentNumber.isEmpty()) {

        if(found) parts.add(currentNumber.toString());
        currentNumber = new StringBuffer();
        found = false;
    }
}

char at(String[] lines, int x, int y) {
    if(x<0 || x>= lines.length || y < 0 || y >= lines[x].length()) return '.';
    return lines[x].charAt(y);
}