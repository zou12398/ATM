
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);
    static ArrayList<Account> allaccount=new ArrayList<>();
    public static void main(String[] args) {


        accountmenu();
    }
    public static void accountmenu(){
        while(true)
        {
            int select;
            if(allaccount.size()==0){
                System.out.println("请注册账户");
                allaccount.add(register());
                continue;
            }
            System.out.println("-----------------------------------");
            System.out.println("1.登录账户");
            System.out.println("2.注册账户");
            System.out.println("请选择你需要的操作:");
            select=sc.nextInt();
            if(select==1) {
                Account account1=enter();
                if (account1==null){
                    continue;
                }
                apimenu(account1);
            }
            else if (select==2) {
                allaccount.add(register());
            }
            else {
                System.out.println("输入错误");
            }
        }
    }

    public static Account enter(){
        while(true){
            System.out.println("请输入账号：");
            String account=sc.next();
            System.out.println("请输入密码:");
            String password=sc.next();
            if(allaccount.size()==0){
                System.out.println("未注册账号");
                return null;
            }
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
    public static Account register(){
        Account anew=new Account();
        System.out.println("请输入名称:");
        anew.setName(sc.next());
        for (int i = 0; i < allaccount.size(); i++) {
            if (anew.getName().equals(allaccount.get(i).getName())){
                while (true) {
                    System.out.println("名字重复，请重新输入:");
                    anew.setName(sc.next());
                    if (anew.getName().equals(allaccount.get(i).getName())){
                    }else {
                        break;
                    }
                }
            }
        }
        System.out.println("请输入密码:");
        anew.setPassword(sc.next());
        System.out.println("请再次输入密码:");
        String newpassword=sc.next();
        while (true) {
            if (anew.getPassword().equals(newpassword)) {
                System.out.println("注册成功");
                anew.setAccount(getRandomcard());
                System.out.println("您的账号是:");
                System.out.println(anew.getAccount());
                break;
            } else {
                System.out.println("密码错误");
                System.out.println("请输入密码:");
                anew.setPassword(sc.next());
                System.out.println("请再次输入密码:");
                newpassword=sc.next();
            }
        }
        System.out.println("请输入账户限额:");
        anew.setLimit(sc.nextDouble());
        return anew;
    }

    private static String getRandomcard() {
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
                    if (i == allaccount.size() - 1) {
                        return newcard;
                    }
                }
            }else {
                return newcard;
            }
        }
    }

    public static void apimenu(Account account1){
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
            switch (select){
                case 1:
                    searech(account1);
                    break;
                case 2:
                    save(account1);
                    break;
                case 3:
                    withdraw(account1);
                    break;
                case 4:
                    transfer(account1);
                    break;
                case 5:
                    change(account1);
                    break;
                case 6:
                    return;
                case 7:
                    cl(account1);
                    break;
                default:
                    System.out.println("输入错误");
                    break;
            }
        }
    }

    private static void cl(Account account1) {
        while (true) {
            System.out.println("您确定要注销账户吗？");
            System.out.println("1.YES");
            System.out.println("2.NO");
            int select = sc.nextInt();
            if (select == 1) {
                for (int i = 0; i < allaccount.size(); i++) {
                    if (allaccount.get(i).getAccount().equals(account1.getAccount())) {
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

    private static void change(Account account1) {
        while (true) {
            System.out.println("请输入原密码：");
            String now = sc.next();
            if (now.equals(account1.getPassword())) {
                System.out.println("请输入新密码:");
                String change1= sc.next();
                System.out.println("请再次输入新密码:");
                String change2=sc.next();
                if(change1.equals(change2)){
                    account1.setPassword(change1);
                    System.out.println("修改成功");
                    break;
                }
            } else {
                System.out.println("原密码错误");
            }
        }
    }


    private static void transfer( Account account1) {
        if (allaccount.size()==1){
            System.out.println("没有可转账对象");
            return;
        }
        System.out.println("您的余额:"+account1.getBalance());
        Account account2;
        OUT:
        while (true) {
            System.out.println("请输入您要转账对象的账号:");
            String change = sc.next();
            if(change.equals(account1.getAccount())){
                System.out.println("输入错误");
                continue ;
            }
            for (int i = 0; i < allaccount.size(); i++) {
                if (allaccount.get(i).getAccount().equals(change)) {
                    account2 = allaccount.get(i);
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
            if(decrease> account1.getBalance()){
                System.out.println("余额不足");
            }else {
                account1.setBalance(account1.getBalance()-decrease);
                account2.setBalance(account2.getBalance()+decrease);
                System.out.println("转账成功");
                break;
            }
        }

    }

    private static void withdraw(Account account1) {
        System.out.println("您的余额:"+account1.getBalance());
        System.out.println("请输入您要取的金额:");
        double decrease=sc.nextDouble();
        if(decrease<= account1.getBalance()) {
            account1.setBalance(account1.getBalance() - decrease);
            System.out.println("取款成功");
        }else {
            System.out.println("余额不足");
        }
    }

    private static void save(Account account1) {
        System.out.println("您的余额:"+account1.getBalance());
        System.out.println("请输入您要存的金额:");
        double more=sc.nextDouble();
        account1.setBalance(account1.getBalance()+more);
        System.out.println("存款成功");
    }

    private static void searech(Account account1) {
        System.out.println("账号:"+account1.getAccount());
        System.out.println("名称:"+account1.getName());
        System.out.println("余额:"+account1.getBalance());
        System.out.println("转账限额:"+account1.getLimit());
    }

}