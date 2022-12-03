package com.edu.filestats.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class CLIRunner implements CommandLineRunner, ExitCodeGenerator {
    // auto-configured to inject PicocliSpringFactory
    @Autowired
    private CommandLine.IFactory factory;
    @Autowired
    private AppCommand cmd;
    private int exitCode;

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(cmd, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}