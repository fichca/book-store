package com.company.storage.file;

import com.company.entity.Author;
import com.company.storage.AuthorStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAuthorStorage extends AbstractFileStorage implements AuthorStorage {

    private final String FORMAT = "%d %s \n";
    private final String CONTAINS = "#ID  FULL_NAME \n";


    public static void main(String[] args) {
        FileAuthorStorage fileAuthorStorage = new FileAuthorStorage();
        fileAuthorStorage.save(new Author("test1"));
        fileAuthorStorage.save(new Author("test2"));
        fileAuthorStorage.save(new Author("test3"));
        System.out.println(Arrays.toString(fileAuthorStorage.getAll()));
    }

    @Override
    public void save(Author author) {

        try {
            FileWriter writer = new FileWriter(AUTHOR_PATH, true);

            if (isEmpty(AUTHOR_PATH)) {
                writer.write(CONTAINS);
            }

            String format = String.format(this.FORMAT, getNewId(), author.getFullName());
            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNewId() {
        List<Author> allList = getAllList();
        if (isEmpty(AUTHOR_PATH)) return 1;
        Author author = allList.get(allList.size() - 1);
        return author.getId() + 1;
    }

    @Override
    public void remove(int id) {
        if (!contains(id)) return;
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getId() == id) {
                allList.remove(author);
                break;
            }
        }
        clean(AUTHOR_PATH);
        for (Author author : allList) {
            saveForRemove(author);
        }


    }

    @Override
    public void remove(String fullName) {
        if (!contains(fullName)) return;
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getFullName().equals(fullName)) {
                allList.remove(author);
                break;
            }
        }
        clean(AUTHOR_PATH);
        for (Author author : allList) {
            saveForRemove(author);
        }
    }

    public void saveForRemove(Author author) {

        try {
            FileWriter writer = new FileWriter(AUTHOR_PATH, true);

            if (isEmpty(AUTHOR_PATH)) {
                writer.write(CONTAINS);
            }

            String format = String.format(this.FORMAT, author.getId(), author.getFullName());
            writer.write(format);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updateFullName(String newFullName, int id) {
        String old = null;
        if (!contains(id)) return null;
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getId() == id) {
                old = author.getFullName();
                author.setFullName(newFullName);
                break;
            }
        }

        clean(AUTHOR_PATH);
        for (Author author : allList) {
            saveForRemove(author);
        }
        return old;
    }

    @Override
    public Author[] getAll() {

        return getAllList().toArray(new Author[0]);
    }

    private List<Author> getAllList() {
        List<Author> authors = new ArrayList<>();
        if (isEmpty(AUTHOR_PATH)) return new ArrayList<>();
        FileReader reader = null;
        try {
            reader = new FileReader(AUTHOR_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("#ID  FULL_NAME ")) continue;
                String[] split = line.split(SPLIT);
                Author author = new Author();
                author.setId(Integer.parseInt(split[0]));
                author.setFullName(split[1]);
                authors.add(author);
            }
            bufferedReader.close();
            return authors;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authors;
    }


    @Override
    public Author getById(int id) {
        if (!contains(id)) return null;
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getId() == id) {
                return author;
            }
        }
        return null;
    }

    @Override
    public Author fullName(String fullName) {
        if (!contains(fullName)) return null;
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getFullName().equals(fullName)) {
                return author;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String fullName) {
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getFullName().equals(fullName)) return true;
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        List<Author> allList = getAllList();
        for (Author author : allList) {
            if (author.getId() == id) return true;
        }
        return false;
    }
}
