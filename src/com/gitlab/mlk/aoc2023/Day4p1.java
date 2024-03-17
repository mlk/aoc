import java.util.Arrays;

String data = """
        """.trim();

void main(String... args) {
    int total = 0;
    int index = 1;
    int[] counts = new int[data.split("\n").length]  ;
    for(var row : data.split("\n")) {
        var cols = row.split("[:|]");
        var winning = Arrays.stream(cols[2].split(" +"))
                .filter( x -> !x.isEmpty())
                .map(Integer::parseInt).toList();
        int c = Arrays.stream(cols[1].split(" +")).filter( x -> !x.isEmpty()).map(Integer::parseInt).filter(winning::contains)
                .mapToInt(x -> x).reduce(0, (x, y) -> {
                    if(x == 0) return 1; else return x * 2;
                });
        total +=c;
    }
    System.out.println(total);
}