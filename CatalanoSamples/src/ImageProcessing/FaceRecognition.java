/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import Catalano.Core.ArraysUtil;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Photometric.TanTriggsNormalization;
import Catalano.Imaging.Texture.BinaryPattern.ImprovedLocalBinaryPattern;
import Catalano.Imaging.Tools.SpatialHistogram;
import Catalano.MachineLearning.Classification.MulticlassSupportVectorMachine;
import Catalano.MachineLearning.Performance.HoldoutValidation;
import Catalano.Statistics.Kernels.ChiSquare;

/**
 * Face recognition similar like D. MATURA approach
 * @author Diego Catalano
 */
public class FaceRecognition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        double[][] data = new double[400][];
        int[] labels = new int[400];
        
        SpatialHistogram sp = new SpatialHistogram(6,6);
        
        //Load the images
        int idx = 0;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 10; j++) {
                
                //Load the images
                FastBitmap fb = new FastBitmap("c:\\files\\faces\\s" + (i+1) + "\\" + (j+1) + "_o.bmp");
                
                //Apply illumination correction
                TanTriggsNormalization tt = new TanTriggsNormalization();
                tt.applyInPlace(fb);
                
                //Compute the spatial histogram
                data[idx] = ArraysUtil.toDouble(sp.Compute(fb, new ImprovedLocalBinaryPattern()));
                labels[idx] = i;
                idx++;
            }
        }
        
        System.out.println("Features extracted.");
        
        MulticlassSupportVectorMachine ml = new MulticlassSupportVectorMachine(new ChiSquare(), 1, 40); 
        HoldoutValidation hv = new HoldoutValidation(0.8f);
        double p = hv.Run(ml, data, labels);
        System.out.println(data[0].length);
        System.out.println(p);
    }
}