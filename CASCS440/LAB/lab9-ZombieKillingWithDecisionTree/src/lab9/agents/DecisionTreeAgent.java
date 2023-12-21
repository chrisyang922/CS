package src.lab9.agents;


// SYSTEM IMPORTS


import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


// JAVA PROJECT IMPORTS
import edu.bu.lab9.agents.SurvivalAgent;
import edu.bu.lab9.dtree.quality.Entropy;
import edu.bu.lab9.linalg.Matrix;
import edu.bu.lab9.utils.Pair;



public class DecisionTreeAgent
    extends SurvivalAgent
{

    public enum FeatureType
    {
        CONTINUOUS,
        DISCRETE
    }

    public static class DecisionTree
        extends Object
    {

        public static class Node
            extends Object
        {
            private int featureIndex;
            private List<Double> threshold;
            private int discreteOutcome;
            private List<Node> listNode;


            public Node()
            {
                featureIndex = 0;
                threshold = null;
                discreteOutcome = 0;
                listNode = null;
            }


        }

        public Node root;
        public static final FeatureType[] FEATURE_HEADER = {FeatureType.CONTINUOUS, //friendliness (lower -- friendlier)
                                                            FeatureType.CONTINUOUS, // shabbyness (less than 0 is good condition, more than 0 is starting to decay)
                                                            FeatureType.DISCRETE, //smell (not very smelly, kinda smelly, very smelly)
                                                            FeatureType.DISCRETE}; // dirty (__, __, __, __)

        public FeatureType[] FEATURE_HEADERTWO = {FeatureType.CONTINUOUS, //friendliness (lower -- friendlier)
                                                            FeatureType.CONTINUOUS, // shabbyness (less than 0 is good condition, more than 0 is starting to decay)
                                                            FeatureType.DISCRETE, //smell (not very smelly, kinda smelly, very smelly)
                                                            FeatureType.DISCRETE}; // dirty (__, __, __, __)

        public DecisionTree()
        {
            this.root = null;
        }

        public Node getRoot() { return this.root; }
        public void SetFeatureType(FeatureType[] f) { this.FEATURE_HEADERTWO = f;}
        private void setRoot(Node n) { this.root = n; }
        

        public void fit(Matrix X, Matrix y_gt)
        { //gt is the ground truth
            // TODO: complete me!
            // System.out.println(X);

            /* 
            List<Integer> featureIndexLst = new ArrayList<>();
            featureIndexLst.add(0);
            featureIndexLst.add(1);
            featureIndexLst.add(2);
            featureIndexLst.add(3);
            double firstThresholdVal = 0.0;
            for (int val = 0; val < X.getShape().getNumRows(); val++)
            {
                firstThresholdVal += X.get(val, 0);
            }
            firstThresholdVal /= X.getShape().getNumRows();

            double secondThresholdVal = 0.0;

            this.root = new Node();
            */

            Node decision = buildDecisionTree(X, y_gt);
            this.setRoot(decision);
        }

        public Node buildDecisionTree(Matrix X, Matrix gt)
        {
            if (X.getShape().getNumCols() == 0)
            {
                Node done = new Node();
                int countOne = 0;
                int countTwo = 0;
                int groundLength = gt.getShape().getNumRows();
                for(int check = 0; check < groundLength; check ++)
                {
                    if(gt.get(check,0) - 0.0 <= Math.abs(0.001))
                    {
                        countOne += 1;
                    }
                    else
                    {
                        countTwo += 1;
                    }
                }
                if (countOne > countTwo)
                {
                    done.discreteOutcome = 0;
                }
                else
                {
                    done.discreteOutcome = 1;
                }
                return done;
            }
            if (this.uniformLabel(gt) == true)
            {
                Node done = new Node();
                done.discreteOutcome = (int)gt.get(0,0);
                return done;
            }
            else
            {
                int bestFeatureIndex = this.determineBestIndex(X, gt);
                Node childNode = new Node();
                childNode.featureIndex = bestFeatureIndex;
                childNode.threshold = threshold(X, bestFeatureIndex);
                if (FEATURE_HEADERTWO[bestFeatureIndex] == FeatureType.CONTINUOUS)
                {
                    childNode.listNode = new ArrayList<Node>();
                    double thresholdValue = childNode.threshold.get(0);
                    Matrix subMatrix = X.getCol(bestFeatureIndex);
                    int numRow = subMatrix.getShape().getNumRows();
                    int numOne = 0;
                    int numTwo = 0;
                    for (int x = 0; x < numRow; x++)
                    {
                        if (subMatrix.get(x, 0) < thresholdValue)
                        {
                            numOne += 1;
                        }
                        else
                        {
                            numTwo += 1;
                        }
                    }
                    Matrix one = Matrix.zeros(numOne, X.getShape().getNumCols()-1);
                    Matrix two = Matrix.zeros(numTwo, X.getShape().getNumCols()-1);
                    Matrix oneGF = Matrix.zeros(numOne, 1);
                    Matrix twoGF = Matrix.zeros(numTwo, 1);
                    int countOne = 0;
                    int countTwo = 0;
                    System.out.println(numOne);
                    System.out.println(numTwo);
                    for (int x = 0; x < numRow; x++)
                    {
                        if (subMatrix.get(x, 0) < thresholdValue)
                        {
                            int in = 0;
                            for(int c = 0; c < X.getShape().getNumCols(); c++)
                            {
                                if (c == bestFeatureIndex)
                                {
                                    continue;
                                }
                                double g = X.get(x,c);
                                one.set(countOne,in, g);
                                oneGF.set(countOne, 0, gt.get(x,0));
                                in ++;
                                
                            }
                            countOne++;
                        }
                        else
                        {
                            int in = 0;
                            for(int c = 0; c < X.getShape().getNumCols(); c++)
                            {
                                if (c == bestFeatureIndex)
                                {
                                    continue;
                                }
                                
                                two.set(countTwo,in, X.get(x,c));
                                twoGF.set(countTwo, 0, gt.get(x,0));
                                in++;
                            
                            }
                            countTwo++;
                        }
                    }
                    this.SetFeatureType(removeIndex(this.FEATURE_HEADERTWO, bestFeatureIndex));
                    childNode.listNode.add(buildDecisionTree(one, oneGF));
                    childNode.listNode.add(buildDecisionTree(two, twoGF));

                    
                    
            
                    
                }
                else
                {
                    childNode.listNode = new ArrayList<Node>();
                    double thresholdOne = childNode.threshold.get(0);
                    double thresholdTwo = childNode.threshold.get(1);
                    double thresholdThree = childNode.threshold.get(2);
                    double thresholdFour = 0.0;
                    boolean shouldCheck = false;
                    if (childNode.threshold.size() > 3)
                    {
                        shouldCheck = true;
                        thresholdFour = childNode.threshold.get(3);
                    }


                    
                    Matrix subMatrix = X.getCol(bestFeatureIndex);
                    int numRow = subMatrix.getShape().getNumRows();
                    int numOne = 0;
                    int numTwo = 0;
                    int numThree = 0;
                    int numFour = 0;
                    for (int x = 0; x < numRow; x++)
                    {
                        if (subMatrix.get(x, 0) - thresholdOne <= Math.abs(0.0001))
                        {
                            numOne += 1;
                        }
                        else if (subMatrix.get(x, 0) - thresholdTwo <= Math.abs(0.0001))
                        {
                            numTwo += 1;
                        }
                        else if (subMatrix.get(x, 0) - thresholdThree <= Math.abs(0.0001))
                        {
                            numThree += 1;
                        }
                        else
                        {
                            numFour += 1;
                        }
                    }

                    Matrix one = Matrix.zeros(numOne, X.getShape().getNumCols()-1);
                    Matrix two = Matrix.zeros(numTwo, X.getShape().getNumCols()-1);
                    Matrix three = Matrix.zeros(numThree, X.getShape().getNumCols()-1);

                    Matrix oneGF = Matrix.zeros(numOne, 1);
                    Matrix twoGF = Matrix.zeros(numTwo, 1);
                    Matrix threeGF = Matrix.zeros(numThree, 1);
                    int countOne = 0;
                    int countTwo = 0;
                    int countThree = 0;
                    int countFour = 0;
                    
                    Matrix four = null;
                    Matrix fourGF = null;
                    if (shouldCheck)
                    {
                        four = Matrix.zeros(numFour, X.getShape().getNumCols()-1);
                        fourGF = Matrix.zeros(numOne, 1);
                    }
                    

                    for (int x = 0; x < numRow; x++)
                    {
                        if (subMatrix.get(x, 0) - thresholdOne <= Math.abs(0.0001))
                        {
                            int in = 0;
                            for(int c = 0; c < X.getShape().getNumCols(); c++)
                            {
                                if (c == bestFeatureIndex)
                                {
                                    continue;
                                }
                                one.set(countOne,in, X.get(x,c));
                                oneGF.set(countOne, 0, gt.get(x,0));
                                in ++;
                                countOne++;
                            }
                        }
                        else if (subMatrix.get(x, 0) - thresholdTwo <= Math.abs(0.0001))
                        {
                            int in = 0;
                            for(int c = 0; c < X.getShape().getNumCols(); c++)
                            {
                                if (c == bestFeatureIndex)
                                {
                                    continue;
                                }
                                two.set(countTwo,in, X.get(x,c));
                                twoGF.set(countTwo, 0, gt.get(x,0));
                                in ++;
                                countTwo ++;
                            }
                        }
                        else if (subMatrix.get(x, 0) - thresholdThree <= Math.abs(0.0001))
                        {
                            int in = 0;
                            for(int c = 0; c < X.getShape().getNumCols(); c++)
                            {
                                if (c == bestFeatureIndex)
                                {
                                    continue;
                                }
                                three.set(countThree,in, X.get(x,c));
                                threeGF.set(countThree, 0, gt.get(x,0));
                                in ++;
                                countThree ++;
                            }
                        }
                        else
                        {
                            if(shouldCheck)
                            {
                                int in = 0;
                                for(int c = 0; c < X.getShape().getNumCols(); c++)
                                {
                                    if (c == bestFeatureIndex)
                                    {
                                        continue;
                                    }
                                    four.set(countFour,in, X.get(x,c));
                                    fourGF.set(countFour, 0, gt.get(x,0));
                                    in ++;
                                    countFour++;
                                }
                            }
                        }
                    }
                    this.SetFeatureType(removeIndex(this.FEATURE_HEADERTWO, bestFeatureIndex));
                    childNode.listNode.add(buildDecisionTree(one, oneGF));
                    childNode.listNode.add(buildDecisionTree(two, twoGF));
                    childNode.listNode.add(buildDecisionTree(three, threeGF));
                    if(shouldCheck == true)
                    {
                        childNode.listNode.add(buildDecisionTree(four, fourGF));
                    }
                    

                }
                

                return childNode;
            }
        }

        public FeatureType[] removeIndex(FeatureType[] original, int indexToRemove) {
            // Check if the index is valid
            if (indexToRemove < 0 || indexToRemove >= original.length) {
                throw new IllegalArgumentException("Index to remove is out of bounds.");
            }
        
            // Create a new array of size one less than the original
            FeatureType[] newArray = new FeatureType[original.length - 1];
        
            // Copy elements from the original array into the new array, skipping the index to remove
            for (int i = 0, j = 0; i < original.length; i++) {
                if (i == indexToRemove) {
                    // Skip the index by not incrementing j
                    continue;
                }
                // Copy the element and increment the index of the new array
                newArray[j++] = original[i];
            }
        
            return newArray;
        }
        
        
        
        
        
        
        public boolean uniformLabel(Matrix gt)
        {
            int numRow = gt.getShape().getNumRows();
            List<Double> lst = new ArrayList<>(numRow);
            for (int r = 0; r < numRow; r++)
            {
                lst.add(gt.get(r, 0));
            }
            Set<Double> unique = new HashSet<>(lst);
            List<Double> uniqueList = new ArrayList<>(unique);       
            int len = uniqueList.size();
            if (len == 1)
            {
                return true;
            }
            return false;
        }
        public int determineBestIndex(Matrix X, Matrix gt)
        {
            System.out.println(X.getShape());
            System.out.println(gt.getShape());
            int numCol = X.getShape().getNumCols();
            double bestEntropyVal = 0.0;
            int numRow = X.getShape().getNumRows();
            int index = 0;
            double sum = 0.0;
            for (int x = 0; x < numCol; x++)
            {
                FeatureType fea = FEATURE_HEADER[x];
                double thresholdVal = 0.0;
                double entropy = 0.0;
                double informationGain = 0.0;
                try{
                    entropy = Entropy.entropy(gt);
                }
                catch (Exception e)
                {
                    System.out.println("error 1");
                }
                
                if (FEATURE_HEADERTWO[x] == FeatureType.CONTINUOUS)
                {
                    for (int r = 0; r < numRow; r++)
                    {
                        thresholdVal += X.get(r, x);
                    }
                    thresholdVal /= numRow;
                    int oneSize = 0;
                    int twoSize = 0;
                    for (int r = 0; r < numRow; r++)
                    {
                        if (thresholdVal <= X.get(r, x))
                        {
                            oneSize += 1;
                        }
                        else
                        {
                            twoSize += 1;
                        }
                    }
                    Matrix one = Matrix.zeros(oneSize, 1);
                    Matrix two = Matrix.zeros(twoSize, 1);
                    double eOne = 0.0;
                    double eTwo = 0.0;
                    int rowcountOne = 0;
                    int rowcountTwo = 0;
                    for (int r = 0; r < numRow; r++)
                    {
                        if (thresholdVal <= X.get(r,x))
                        {
                            one.set(rowcountOne, 0, gt.get(r, 0));
                            rowcountOne ++; 
                        }
                        else
                        {
                            two.set(rowcountTwo,0,gt.get(r,0));
                            rowcountTwo++;
                        }
                    }
                    try
                    {
                        eOne = Entropy.entropy(one);
                        eTwo = Entropy.entropy(two);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    informationGain = entropy - eOne - eTwo;
                    if (informationGain > bestEntropyVal)
                    {
                        bestEntropyVal = informationGain;
                        index = x;
                    }

                }
                else
                {
                    List<Double> lst = new ArrayList<>(numRow);
                    for (int r = 0; r < numRow; r++)
                    {
                        lst.add(X.get(r, x));
                    }
                    Set<Double> unique = new HashSet<>(lst);
                    List<Double> uniqueList = new ArrayList<>(unique);       
                    int len = uniqueList.size();
                    int oneSize = 0;
                    int twoSize = 0;
                    int threeSize = 0;
                    int fourSize = 0;
                    for (int r = 0; r < numRow; r++)
                    {
                        if (1.0 - X.get(r, x) <= Math.abs(0.001))
                        {
                            oneSize += 1;
                        }
                        else if (2.0 - X.get(r, x) <= Math.abs(0.001))
                        {
                            twoSize += 1;
                        }
                        else if ((3.0 - X.get(r, x) <= Math.abs(0.001)))
                        {
                            threeSize += 1;
                        }
                        else
                        {
                            fourSize += 1;
                        }
                    }
                    Matrix one = Matrix.zeros(oneSize, 1);
                    Matrix two = Matrix.zeros(twoSize, 1);
                    Matrix three = Matrix.zeros(threeSize, 1);
                    Matrix four = null;
                    if (fourSize > 0)
                    {
                        four = Matrix.zeros(fourSize, 1);
                    }
                    double eOne = 0.0;
                    double eTwo = 0.0;
                    double eThree =0.0;
                    double eFour = 0.0;
                    int rowCountOne = 0;
                    int rowCountTwo = 0;
                    int rowCountThree = 0;
                    int rowCountFour = 0;
                    for (int r = 0; r < numRow; r++)
                    {
                        if (1.0 - X.get(r, x) <= Math.abs(0.001))
                        {
                            one.set(rowCountOne, 0, gt.get(r,0));
                            rowCountOne++;
                        }
                        else if (2.0 - X.get(r, x) <= Math.abs(0.001))
                        {
                            two.set(rowCountTwo, 0, gt.get(r,0));
                            rowCountTwo++;
                        }
                        else if ((3.0 - X.get(r, x) <= Math.abs(0.001)))
                        {
                            three.set(rowCountThree, 0, gt.get(r,0));
                            rowCountThree++;
                        }
                        else
                        {
                            
                            four.set(rowCountFour, 0, gt.get(r,0));
                            rowCountFour++;
                        }
                    }
                    try{
                        eOne = Entropy.entropy(one);
                        eTwo = Entropy.entropy(two);
                        eThree = Entropy.entropy(three);
                        if (fourSize > 0)
                        {
                            eFour = Entropy.entropy(four);
                        }
                    } 
                    catch(Exception e)
                    {

                    }
                    informationGain = entropy - eOne - eTwo - eThree;
                    if(fourSize > 0)
                    {
                        informationGain -= eFour;
                    }
                    if (informationGain > bestEntropyVal)
                    {
                        bestEntropyVal = informationGain;
                        index = x;
                    }
                    

                }
                

            }



            return index;
        }
        
        public List<Double> threshold(Matrix X, int i)
        {
            int numRow = X.getShape().getNumRows();
                
                double thresholdVal = 0.0;
  
                
                if (FEATURE_HEADERTWO[i] == FeatureType.CONTINUOUS)
                {
                    for (int r = 0; r < numRow; r++)
                    {
                        thresholdVal += X.get(r, i);
                    }
                    thresholdVal /= numRow;
                    List<Double> doubleLst = new ArrayList<>();
                    doubleLst.add(thresholdVal);
                    return doubleLst;
                }
                else
                {
                    List<Double> lst = new ArrayList<>(numRow);
                    for (int r = 0; r < numRow; r++)
                    {
                        lst.add(X.get(r, i));
                    }
                    Set<Double> unique = new HashSet<>(lst);
                    List<Double> uniqueList = new ArrayList<>(unique);       
                    int len = uniqueList.size();
                    List<Double> doubleLst = new ArrayList<>();
                    doubleLst.add(0.0);
                    doubleLst.add(1.0);
                    doubleLst.add(2.0);
                    if (len > 3)
                    {
                        doubleLst.add(3.0);
                    }
                    return doubleLst;
                }
            
            
        }
        public int predict(Matrix x)
        {
            // TODO: complete me!
            // class 0 means Human (i.e. not a zombie), class 1 means zombie

            FeatureType[] FEATURE_HEADERThree = {FeatureType.CONTINUOUS, //friendliness (lower -- friendlier)
                                                            FeatureType.CONTINUOUS, // shabbyness (less than 0 is good condition, more than 0 is starting to decay)
                                                            FeatureType.DISCRETE, //smell (not very smelly, kinda smelly, very smelly)
                                                            FeatureType.DISCRETE}; // dirty (__, __, __, __)
            Node prediction = this.getRoot();
            int count = 0;
            while(prediction.listNode != null)
            {
                System.out.println(count);
                count++;
                int numF = x.getShape().getNumCols();
                int index = prediction.featureIndex;
                if(FEATURE_HEADERThree[index] == FeatureType.CONTINUOUS)
                {
                    double threshold = prediction.threshold.get(0);
                    if(x.get(0, index) < threshold)
                    {
                        prediction = prediction.listNode.get(0);
                        
                        Matrix newMatrix = Matrix.zeros(1, numF-1);
                        int ccc = 0;
                        for (int s = 0; s < numF; s++)
                        {
                            if (s == index)
                            {
                                continue;
                            }
                            double val = x.get(0,s);
                            newMatrix.set(0,ccc, val);
                            
                            ccc++;
                        }
                        x = newMatrix;
                    }
                    else
                    {
                        prediction = prediction.listNode.get(1);
                        
                        Matrix newMatrix = Matrix.zeros(1, numF-1);
                        int ccc = 0;
                        for (int s = 0; s < numF; s++)
                        {
                            if (s == index)
                            {
                                continue;
                            }
                            double val = x.get(0,s);
                            newMatrix.set(0,ccc, val);
                            
                            ccc++;
                        }
                        x = newMatrix;
                    }
                    FEATURE_HEADERThree = removeIndex(FEATURE_HEADERThree, index);
                }
                else
                {
                    for(int y = 0; y < prediction.threshold.size(); y++)
                    {
                        double threshold = prediction.threshold.get(y);
                        if(threshold - x.get(0,index) <= Math.abs(0.001))
                        {
                            prediction = prediction.listNode.get(y);
                            
                            Matrix newMatrix = Matrix.zeros(1, numF-1);
                            int ccc = 0;
                            for (int s = 0; s < numF; s++)
                            {

                                if (s == index)
                                {
                                    continue;
                                }
                                double val = x.get(0,s);
                                newMatrix.set(0,ccc, val);
                                
                                ccc++;
                            }
                            x = newMatrix;
                        }
                    }
                    FEATURE_HEADERThree = removeIndex(FEATURE_HEADERThree, index);
                }
                
            }
            

            return prediction.discreteOutcome;
        }

    }

    private DecisionTree tree;

    public DecisionTreeAgent(int playerNum, String[] args)
    {
        super(playerNum, args);
        this.tree = new DecisionTree();
    }

    public DecisionTree getTree() { return this.tree; }

    @Override
    public void train(Matrix X, Matrix y_gt)
    {
        System.out.println(X.getShape() + " " + y_gt.getShape());
        this.getTree().fit(X, y_gt);
    }

    @Override
    public int predict(Matrix featureRowVector)
    {
        return this.getTree().predict(featureRowVector);
    }

}
