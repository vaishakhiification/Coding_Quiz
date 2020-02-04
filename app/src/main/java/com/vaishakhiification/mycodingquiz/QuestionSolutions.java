package com.vaishakhiification.mycodingquiz;

public class QuestionSolutions {
    String question;
    String solution;
    int limit;
    int noOfChances;

    QuestionSolutions(String question, String solution, int limit) {
        this.question = question;
        this.solution = solution;
        this.limit = limit;
    }

    boolean checkSolution(String submittedSolution) {
        if (submittedSolution.trim().equalsIgnoreCase(solution.trim())) {
            return true;
        }
        return false;
    }

    boolean checkLimit() {
        if (noOfChances >= limit) {
            return true;
        }
        return false;
    }

    void incrementNoOfChances() {
        noOfChances++;
    }
}
