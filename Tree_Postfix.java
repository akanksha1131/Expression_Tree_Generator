import java.util.Stack;
import java.util.*;
public class Tree_Postfix {
    public static class Node{
        char data;
        Node left;
        Node right;

        public Node(char data){
            //Assign data to the new node, set left and right children to null
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    //Represent the root of binary tree
    public Node root;

    public Tree_Postfix(){
        root = null;
    }

    public static Node Construct_expression(String input){
        Tree_Postfix tree=new Tree_Postfix();
        Stack<Node> root_stack=new Stack<>();
        Stack<Node> operand_stack=new Stack<>();
        Node t1,t2;

        for(int i=0;i<input.length();i++){
            if(Character.isDigit(input.charAt(i)) ||
                    Character.isLetter(input.charAt(i))){
                Node newnode=new Node(input.charAt(i));
                //System.out.println("----------------  "+newnode.data);
                operand_stack.push(newnode);

            }
            else if( (input.charAt(i)=='+' || input.charAt(i)=='-'|| input.charAt(i)=='*' || input.charAt(i)=='/' || input.charAt(i)=='^')){
                Node root1=new Node(input.charAt(i));
                // System.out.println("root1----------------  "+root1.data);
                t2=operand_stack.pop();
                // System.out.println("opn stack pop----------------  "+t2.data);
                t1=operand_stack.pop();
                // System.out.println("opn stack pop----------------  "+t1.data);
                root1.left=t1;
                root1.right=t2;
                tree.root=root1;
                //System.out.println(root1);
                operand_stack.push(root1);

            }
        }
        // System.out.println("*********************** "+operand_stack.peek());
        return tree.root;
    }
    static void inorder_traverse(Node root){

        if (root==null){
            return;
        }
        else{
            inorder_traverse(root.left);
            System.out.print("→ ("+root.data+") ");
            inorder_traverse(root.right);
        }
    }
    static void preorder_traverse(Node root){

        if (root==null){
            return;
        }
        else{
            System.out.print("→ ("+root.data+") ");
            preorder_traverse(root.left);
            preorder_traverse(root.right);
        }
    }
    static void postorder_traverse(Node root){

        if (root==null){
            return;
        }
        else{
            postorder_traverse(root.left);
            postorder_traverse(root.right);
            System.out.print("→ ("+root.data+") ");
        }
    }
    Stack<Character>stack =new Stack<Character >();
    String postfix="";
    static int check_priority(char a){
        int priority=0;
        if( a=='+' || a=='-'){
            priority=0;
        }
        else if( a=='*' || a=='/'){
            priority=1;
        }
        else if( a=='^'){
            priority=2;
        }
        else{
            priority=-1;
        }
        return priority;
    }
    void postfix_exp(String s ){
        for(int i=0;i<s.length();i++){

            if(Character.isDigit(s.charAt(i)) || Character.isLetter(s.charAt(i))){
                postfix=postfix+s.charAt(i);

            }
            else if(s.charAt(i)=='('){
                stack.push(s.charAt(i));
            }
            /////////// step 4
            else if(s.charAt(i)=='+' || s.charAt(i)=='-' || s.charAt(i)=='/' || s.charAt(i)=='*' || s.charAt(i)=='^'){

                if(stack.isEmpty()){
                    stack.push(s.charAt(i));

                }
                else if(!stack.isEmpty() && stack.peek() == '('){

                    stack.push(s.charAt(i));
                }
                else{
                    while(!stack.isEmpty() &&
                            check_priority((s.charAt(i))) < check_priority(stack.peek())){
                        postfix=postfix+stack.pop();

                    }
                    stack.push(s.charAt(i));
                }
            }
            else if(s.charAt(i)==')'){
                while (!stack.isEmpty() && stack.peek() != '('){
                    postfix=postfix+stack.pop();
                }
            }
            if(i==s.length()-1){
                while (!stack.isEmpty() && stack.peek() != '('){
                    postfix=postfix+stack.pop();
                }
            }
            else{

            }

        }
    }
    void evaluate_postfix(String postfix){
        Stack<Integer>stackv =new Stack<Integer>();
        int op1,op2,value;
        for(int i=0;i<postfix.length();i++){
            if(Character.isDigit(postfix.charAt(i))){
                stackv.push(Character.getNumericValue(postfix.charAt(i)));
            }
            else if(postfix.charAt(i)=='+' || postfix.charAt(i)=='-' ||
                    postfix.charAt(i)=='/' ||
                    postfix.charAt(i)=='*'||postfix.charAt(i)=='^' ){
                if(postfix.charAt(i)=='+'){
                    op2=stackv.pop();
                    op1=stackv.pop();
                    value=op1+op2;
                    stackv.push(value);
                }
                if(postfix.charAt(i)=='-'){
                    op2=stackv.pop();
                    op1=stackv.pop();
                    value=op1-op2;
                    stackv.push(value);
                }
                if( postfix.charAt(i)=='/'){
                    op2=stackv.pop();
                    op1=stackv.pop();
                    value=op1/op2;
                    stackv.push(value);
                }
                if(postfix.charAt(i)=='*'){
                    op2=stackv.pop();
                    op1=stackv.pop();
                    value=op1*op2;
                    stackv.push(value);
                }
                if(postfix.charAt(i)=='^'){
                    op2=stackv.pop();
                    op1=stackv.pop();
                    double
                            value1=Math.pow(Double.valueOf(op1),Double.valueOf(op2));
                    stackv.push((int)value1);
                }
            }
        }
        System.out.println(stackv);
    }


    public static void main(String[] args){
        Tree_Postfix obj=new Tree_Postfix();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter infix expression: ");
        String input=sc.next();
        obj.postfix_exp(input);
        System.out.println("Postfix expression: ");
        System.out.println(obj.postfix);
        System.out.println("Evaluation of postfix expression : ");
        obj.evaluate_postfix(obj.postfix);


        System.out.println("Creating binary tree out of the postorder expression: ");
        //Creating binary Tree out of postfix expression

        Node exp=Construct_expression(obj.postfix);
        System.out.println("Inorder traversal:");
        inorder_traverse(exp);
        System.out.println("\nPreorder traversal:");
        preorder_traverse(exp);
        System.out.println("\nPostorder traversal:");
        postorder_traverse(exp);

    }
}