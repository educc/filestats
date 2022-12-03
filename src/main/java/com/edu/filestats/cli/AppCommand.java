package com.edu.filestats.cli;

import com.edu.filestats.FileStatistic;
import com.edu.filestats.statistics.ContentStatistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "AppCommand",
        mixinStandardHelpOptions = true,
        version = "app-cli-1.0",
        description = "Process files")
@Slf4j
public class AppCommand implements Callable<Integer> {

    private FileStatistic fileStats;

    private static final String OUTPUT_DIRNAME = "processed";

    @CommandLine.Option(names = {"-i", "--in-dir"},
            paramLabel = "INPUT_DIR",
            description = "Directory to process files",
            required = true)
    private String inputDir;

    @CommandLine.Option(names = {"-ex-s", "--exclude-strategies"},
            paramLabel = "EXCLUDE_STRATEGIES",
            description = "Comma separated list of strategies name.",
            split = ",")
    private List<String> excludeStrategies = new ArrayList<>();

    @CommandLine.Option(names = {"-ex-r", "--exclude-readers"},
            paramLabel = "EXCLUDE_READERS",
            description = "Comma separated list of readers name.",
            split = ",")
    private List<String> excludeReaders = new ArrayList<>();


    public AppCommand(FileStatistic fileStats) {
        this.fileStats = fileStats;
    }


    @Override
    public Integer call() throws Exception {
        createOutDir(inputDir);

        fileStats.excludeStrategies(excludeStrategies);
        fileStats.excludeReaders(excludeReaders);

        // showing readers
        log.info("Readers to be used:");
        fileStats.getReaderList().stream().map(it -> "- " + it).forEach(log::info);

        // showing strategies
        log.info("Strategies to be used:");
        fileStats.getStrategies().stream().map(it -> "- " + it).forEach(log::info);


        List<String> listOfFiles = Files.list(Paths.get(inputDir))
                .filter(it -> it.toFile().isFile())
                .map(it -> it.toAbsolutePath().toString())
                .toList();

        listOfFiles
                .stream().map(it -> new FileStats(it, fileStats.from(it)))
                .filter(it -> !it.stats.isEmpty())
                .forEach(AppCommand::handleFileStats);

        return 0;
    }


    private static void handleFileStats(FileStats stats) {
        // printing result
        System.out.println("File processed: " + stats.absPath);
        stats.stats.stream().forEach(it -> {
            System.out.println(String.format("%s: %s", it.name(), it.value().toString()));
        });

        // moving file
        var current = Paths.get(stats.absPath);
        var destination = current.getParent().resolve(OUTPUT_DIRNAME).resolve(current.getFileName());
        System.out.println(current + " " + destination);
        try {
            Files.move(current, destination);
        } catch (IOException e) {
            throw new RuntimeException("At moving file to " + OUTPUT_DIRNAME, e);
        }
    }

    private static void createOutDir(String inputDir) {
        Path processed = Paths.get(inputDir, OUTPUT_DIRNAME);
        try {
            Files.createDirectories(processed).toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    record FileStats(String absPath, List<ContentStatistic> stats) {
    }
}
