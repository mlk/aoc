package com.github.mlk.devterm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThermalPrinter {

    public static void main(String[] args) throws IOException {
        print("WOOT");
    }

    public static void print(String data) throws IOException {
        var font = new String(new char[] { 0x1d, 0x21, 0x0 });

        Files.writeString(Path.of("/tmp/DEVTERM_PRINTER_IN"), font + data + "\n".repeat(50));
    }
}
