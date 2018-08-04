import java.util.Scanner;

public class Main {

    private BookDao bookDao;
    private Scanner scanner;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run(){
       bookDao = new BookDao();
       scanner = new Scanner(System.in);
       menu();
    }

    private void menu(){
        System.out.println("\n1 --> Wczytaj\n2 --> Wyszukaj\n3 --> Wyjdz z programu");
        int choiceMenu = 0;

        do{
            System.out.print("\n>> ");
            choiceMenu = scanner.nextInt();
        } while( choiceMenu<1 || choiceMenu>5);

        switch (choiceMenu){
            case 1:
                librarySave();
                break;
            case 2:
                libraryRead();
                break;
            case 3:

            case 4:
                libraryEnd();
                break;
        }
    }

    private void librarySave(){
        Book book = new Book();
        scanner.nextLine();
        bookDao.save(wczytajDane(book));
        menu();
    }

    private Book wczytajDane(Book book) {
        System.out.print("\nPodaj tytuł: ");
        book.setTitle(scanner.nextLine());
        System.out.print("Podaj autora: ");
        book.setAuthor(scanner.nextLine());
        System.out.print("Podaj rok wydania: ");
        book.setYear(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Podaj ISBN: ");
        book.setIsbn(scanner.nextLine());
        return book;
    }

    private void libraryEnd(){
        bookDao.close();
        System.out.println("Do zobaczenia");
    }

    private void libraryRead(){
        scanner.nextLine();
        System.out.print("\nPodaj ISBN: ");
        Book book = bookDao.read(scanner.nextLine());
        System.out.println(book);
        String mod;
        do{
            System.out.print("\nAktualizuj dane (A) | Usuń rekord (U) | Powrót do menu (M)\n>> ");
            mod = scanner.nextLine();
        } while(!mod.equals("A") & !mod.equals("U") & !mod.equals("M"));
        if(mod.equals("A")){
            bookDao.update(wczytajDane(book));
            menu();
        } else if(mod.equals("U")) {
            bookDao.delete(book);
            menu();
        } else {
            menu();
        }
    }
}
