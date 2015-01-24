// this program show that methods can be overridden. Attributes cannot.
// output result and answer is "easy"
package scjp.testclass.inherit;

class Exam {
    protected String difficultyLevel = "easy";
    public void printDifficultyLevel() {
        System.out.println(difficultyLevel);
    }
}


class SCJPExam extends Exam {
    private String difficultyLevel = "killing";
}


public class AttributesOverrideen {
    public static void main(String[] args) {
        SCJPExam myExam = new SCJPExam();
        myExam.printDifficultyLevel();

        Exam parent = new Exam();
        SCJPExam son = (SCJPExam) parent;// it will be a runtime exception
    }
}
