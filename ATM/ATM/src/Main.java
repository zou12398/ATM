import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Account> allaccount=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        accountmenu(allaccount,sc);
    }
    public static void accountmenu(ArrayList<Account> allaccount,Scanner sc){
        while(true)
        {
            int select;
            System.out.println("-----------------------------------");
            System.out.println("1.登录账户");
            System.out.println("2.注册账户");
            System.out.println("请选择你需要的操作:");
            select=sc.nextInt();
            if(select==1) {
                Account Account1=enter(allaccount,sc);
                apimenu(allaccount,sc,Account1);
            }
            else if (select==2) {
                allaccount.add(register(allaccount,sc));
            }
            else {
                System.out.println("输入错误");
            }
        }
    }

    public static Account enter(ArrayList<Account> allaccount,Scanner sc){
        while(true){
            System.out.println("请输入账号：");
            String account=sc.next();
            System.out.println("请输入密码:");
            String password=sc.next();
            for (int i = 0; i <allaccount.size() ; i++) {
                String okaccount=allaccount.get(i).getAccount();
                String okpassword=allaccount.get(i).getPassword();
                if (okaccount.equals(account) && okpassword.equals(password)) {
                    System.out.println("登录成功");
                    return allaccount.get(i);
                }
                if(i== allaccount.size()-1) {
                    System.out.println("账号或密码错误");
                }
            }
        }
    }
    public static Account register(ArrayList<Account> allaccount,Scanner sc){
        Account New=new Account();
        System.out.println("请输入名称:");
        New.setName(sc.next());
        System.out.println("请输入密码:");
        New.setPassword(sc.next());
        System.out.println("请再次输入密码:");
        String newpassword=sc.next();
        System.out.println("请再次输入账户限额:");
        New.setLimit(sc.nextDouble());
        while (true) {
            if (New.getPassword().equals(newpassword)) {
                System.out.println("注册成功");
                New.setAccount(getRandomcard(allaccount));
                System.out.println("您的账号是:");
                System.out.println(New.getAccount());
                break;
            } else {
                System.out.println("密码错误");
                System.out.println("请输入密码:");
                New.setPassword(sc.next());
                System.out.println("请再次输入密码:");
                newpassword=sc.next();
            }
        }
        return New;
    }

    private static String getRandomcard(ArrayList<Account> allaccount) {
        Random r=new Random();
        String newcard="";
        while (true){
            for (int i = 0; i < 8; i++) {
                newcard+=r.nextInt(10);
            }
            if(allaccount.size()>0) {
                for (int i = 0; i < allaccount.size(); i++) {
                    if (newcard.equals(allaccount.get(i).getAccount())) {
                        newcard = "";
                        break;
                    }
                    if (i == allaccount.size() - 1)
                        return newcard;
                }
            }else return newcard;
        }
    }

    public static void apimenu(ArrayList<Account> allaccount,Scanner sc,Account Account1){
        int select;
        while (true) {
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3、取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销账户");
            System.out.println("请选择:");
            select=sc.nextInt();
            if(select==1){
                searech(Account1);
            } else if (select==2) {
                save(Account1,sc);
            } else if (select==3) {
                withdraw(Account1,sc);
            } else if (select==4) {
                transfer(allaccount,Account1,sc);
            } else if (select==5) {
                change(Account1,sc);
            } else if (select==6) {
                break;
            } else if (select==7) {
                cl(Account1,allaccount,sc);
            }else {
                System.out.println("输入错误");
            }
        }
    }

    private static void cl(Account Account1, ArrayList<Account> allaccount,Scanner sc) {
        while (true) {
            System.out.println("您确定要注销账户吗？");
            System.out.println("1.YES");
            System.out.println("2.NO");
            int select = sc.nextInt();
            if (select == 1) {
                for (int i = 0; i < allaccount.size(); i++) {
                    if (allaccount.get(i).getAccount().equals(Account1.getAccount())) {
                        allaccount.remove(i);
                        System.out.println("注销成功");
                    }
                }
            } else if (select == 2) {
                return;
            } else {
                System.out.println("输入错误");
            }
        }
    }

    private static void change(Account Account1, Scanner sc) {
        while (true) {
            System.out.println("请输入原密码：");
            String now = sc.next();
            if (now.equals(Account1.getPassword())) {
                System.out.println("请输入新密码:");
                String change1= sc.next();;
                System.out.println("请再次输入新密码:");
                String change2=sc.next();
                if(change1.equals(change2)){
                    Account1.setPassword(change1);
                    System.out.println("修改成功");
                    break;
                }
            } else {
                System.out.println("原密码错误");
            }
        }
    }


    private static void transfer(ArrayList<Account> allaccount, Account Account1, Scanner sc) {
        System.out.println("您的余额:"+Account1.getBalance());
        Account Account2;
        OUT:
        while (true) {
            System.out.println("请输入您要转账对象的账号:");
            String change = sc.next();
            for (int i = 0; i < allaccount.size(); i++) {
                if (allaccount.get(i).getAccount().equals(change)) {
                    Account2 = allaccount.get(i);
                    break OUT;
                }
                if (i == allaccount.size() - 1) {
                    System.out.println("您输入的账号不存在");
                }
            }
        }
        while (true) {
            System.out.println("请输入您要转账的金额:");
            double decrease = sc.nextDouble();
            if(decrease> Account1.getBalance()){
                System.out.println("余额不足");
            }else {
                Account1.setBalance(Account1.getBalance()-decrease);
                Account2.setBalance(Account2.getBalance()+decrease);
                System.out.println("转账成功");
                break;
            }
        }

    }

    private static void withdraw(Account Account1, Scanner sc) {
        System.out.println("您的余额:"+Account1.getBalance());
        System.out.println("请输入您要取的金额:");
        double decrease=sc.nextDouble();
        if(decrease<= Account1.getBalance()) {
            Account1.setBalance(Account1.getBalance() - decrease);
            System.out.println("取款成功");
        }else {
            System.out.println("余额不足");
        }
    }

    private static void save(Account Account1, Scanner sc) {
        System.out.println("您的余额:"+Account1.getBalance());
        System.out.println("请输入您要存的金额:");
        double more=sc.nextDouble();
        Account1.setBalance(Account1.getBalance()+more);
        System.out.println("存款成功");
    }

    private static void searech(Account Account1) {
        System.out.println("账号:"+Account1.getAccount());
        System.out.println("名称:"+Account1.getName());
        System.out.println("余额:"+Account1.getBalance());
        System.out.println("转账限额:"+Account1.getLimit());
    }

}