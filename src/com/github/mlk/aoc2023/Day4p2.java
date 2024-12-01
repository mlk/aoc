
import java.util.Arrays;

String data = """
        
        """.trim();

void main(String... args) {
    int total = 0;
    int index = 0;
    int[] counts = new int[data.split("\n").length]  ;
    Arrays.fill(counts, 1);
    for(var row : data.split("\n")) {
        var cols = row.split("[:|]");
        var winning = Arrays.stream(cols[2].split(" +"))
                .filter( x -> !x.isEmpty())
                .map(Integer::parseInt).toList();
        int c = (int)Arrays.stream(cols[1].split(" +"))
                .filter( x -> !x.isEmpty()).map(Integer::parseInt).filter(winning::contains)
                .count();

        for(int i = 1; i <= c; i++){
            if(index + i > counts.length) break;

            counts[index + i] += counts[index];
        }

        index++;
    }
    System.out.println(Arrays.stream(counts).sum());
}