package com.example.patrice.bakingapp;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tegan on 9/4/2017.
 */

public class JsonParsingTest {

    @Test
    public void fileObjectShouldNotBeNull() throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("assets/recipes.json");
        Assert.assertNotNull(in);

    }
    @Test
    public void ingredient_parse_isCorrect(){

    }
}
