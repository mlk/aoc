package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Day07 {
    static String data = """
            <retracted>
            """;

    static String data1 = """
            <retracted>""";

        public static void main(String... a) {
            Dir root = new Dir("/", null, new ArrayList<>(), new ArrayList<>());
            Dir current = root;

            for(String line : data1.split("\n")) {
                if(line.startsWith("$")) {
                    if(line.contains("cd")) {
                        String folder = line.split(" ")[2].trim();
                        if(folder.equals("..")) {
                            current = current.root();
                        } else if (folder.equals("/")) {
                            current = root;
                        } else {
                            current = current.sub(folder);
                        }
                    }
                } else {
                    // Command Output (currently only LS)
                    if(line.startsWith("dir")) {

                    } else {
                        current.files().add(File.parse(line));
                    }
                }
            }
            int totalSize =  70000000;
            int requiredSize = 30000000;
            int currentData = root.size();
            int unusedSpace = totalSize - currentData;
            int required = requiredSize - unusedSpace;

            System.out.println("unused space" + unusedSpace);
            System.out.println("Required space" + required);

            List<Dir> found = root.findMinSize(required);
            found.sort(Comparator.comparingInt(Dir::size));
            System.out.println(found.get(0).name + " " + found.get(0).size());
        }

        record Dir(String name, Dir root, List<File> files, List<Dir> dirs) {
            Dir sub(String subName) {
                Optional<Dir> d = dirs.stream().filter(x -> x.name.equals(subName)).findFirst();
                if(d.isPresent()) {
                    return d.get();
                }
                Dir newDir = d.orElse(new Dir(subName, this, new ArrayList<>(), new ArrayList<>()));
                dirs.add(newDir);
                return newDir;
            }

            List<Dir> findMaxSize(int size) {
                List<Dir> results = new ArrayList<>();
                for(Dir d : dirs) {
                    if(d.size() <= size) {
                        results.add(d);
                    }
                    results.addAll(d.findMaxSize(size));
                }
                return results;
            }

            List<Dir> findMinSize(int size) {
                List<Dir> results = new ArrayList<>();
                for(Dir d : dirs) {
                    if(d.size() >= size) {
                        results.add(d);
                    }
                    results.addAll(d.findMinSize(size));
                }
                return results;
            }

            int size() {
                return files.stream().map(x -> x.size).reduce(Integer::sum).orElse(0) + dirs.stream().map(Dir::size).reduce(Integer::sum).orElse(0);
            }

            @Override
            public String toString() {
                return "Dir[" + name + ", files=" + files() + ", dirs=" + dirs + "]";
            }
        }

        record File(int size, String name) {
            static File parse(String data) {
                return new File(Integer.parseInt(data.split(" ")[0]), data.split(" ")[1]);
            }
        }
}
