# App

To process files and get statistics.

# Not implemented

- I did many things this weekend so I did not have enough time to implement the following :

- Create this program as a daemon to watch files.
- Get the statistics for files in parallel.
- It does not ready line by line. The code loads the whole file in memory so it will crash when you put large files.
- UML class diagram made by hand, only auto generated from code.

# Requirements

- JDK 17

# How to build

Linux or Mac:

```shell
chmod +x mvnw
./mvnw install
```

# How to run

After the build you can execute.

```
java -jar target/filestats-0.0.1-SNAPSHOT.jar -i tmp

#excluding readers
java -jar target/filestats-0.0.1-SNAPSHOT.jar -i tmp -ex-r txt

#excluding strategies
java -jar target/filestats-0.0.1-SNAPSHOT.jar -i tmp -ex-s countWords,mostUsedWord

#excluding readers and strategies
java -jar target/filestats-0.0.1-SNAPSHOT.jar -i tmp -ex-r txt -ex-s countWords
```

# How to extend

To support more files you only need:

- Create a new class in the ```com.edu.filestats.contentreader.internal``` package.
- The new class must inherits ```ContentReader```

To support more statistics you only need:

- Create a new class in the ```com.edu.filestats.statistics.internal;``` package.
- The new class must inherits ```StatisticStrategy```

Compile and run again, the code will take care of the new classes and automatically will use the new classes.
This follows the Open-Close principle from SOLID principles.

# App arguments

```plain
Usage: AppCommand [-hV] -i=INPUT_DIR [-ex-r=EXCLUDE_READERS[,
                  EXCLUDE_READERS...]]... [-ex-s=EXCLUDE_STRATEGIES[,
                  EXCLUDE_STRATEGIES...]]...
Process files
      -ex-r, --exclude-readers=EXCLUDE_READERS[,EXCLUDE_READERS...]
                           Comma separated list of readers name.
      -ex-s, --exclude-strategies=EXCLUDE_STRATEGIES[,EXCLUDE_STRATEGIES...]
                           Comma separated list of strategies name.
  -h, --help               Show this help message and exit.
  -i, --in-dir=INPUT_DIR   Directory to process files
  -V, --version            Print version information and exit.
```