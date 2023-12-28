package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class RichMan {
    private int rank;
    private String name;
    private double networth;
    private int age;
    private String country;
    private String source;
    private String industry;


    public RichMan(int rank,
                   String name,
                   double networth,
                   int age,
                   String country,
                   String source,
                   String industry) {

        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.country = country;
        this.source = source;
        this.industry = industry;
    }


    public static List<RichMan> parseCSV(Path path) {
        try (Stream<String> csvLines = Files.lines(path)) {
            return csvLines.skip(1)
                    .map(RichMan::parseLine)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private static RichMan parseLine(String line) {
        String[] values = line.split(",");

        int rank = Integer.parseInt(values[0].replaceAll("\"", ""));
        String name = values[1].replaceAll("\"", "");
        double networth = Double.parseDouble(values[2].replaceAll("\"", ""));
        int age = Integer.parseInt(values[3].replaceAll("\"", ""));
        String country = values[4].replaceAll("\"", "");
        String source = values[5].replaceAll("\"", "");
        String industry = values[6].replaceAll("\"", "");

        return new RichMan(rank, name, networth, age, country, source, industry);
    }



    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getNetworth() {
        return rank;
    }
    public void setNetworth(int networth) {
        this.networth = networth;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }



    @Override
    public String toString() {
        return "RichMan{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", networth='" + networth + '\'' +
                ", age='" + age + '\'' +
                ", country='" + country + '\'' +
                ", source=" + source +
                ", industry=" + industry +
                '}';
    }
}
