package httpserver;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import MultiLayerNN.NeuralNetwork;
import NumbersRecognition.Utils;

public class NN {
	static NeuralNetwork nn;
	static List<String> onname = List.of("0","1","2","3","4","5","6","7","8","9");

	public static void main(String[] args) throws IOException {
		//if(nn==null) {
			//nn =  (NeuralNetwork) DataHandle.load();
			//nn = ds.nn;
			//onname = ds.onname;
		System.out.println(Arrays.toString(Utils.arrayofblackwhite(ImageIO.read(new File("D:\\Users\\Juweriah\\Documents\\Eclipse\\eclipse\\Test\\MNIST_Database_ARGB\\1_04572.png")))));
		try {
			System.out.println(	((NeuralNetwork) Utils.load(new File("D:\\Users\\Juweriah\\Documents\\Eclipse\\eclipse\\NeuralNetwork\\src\\ImageMNIST\\nn.dat"))).predict(Utils.to1d2image(ImageIO.read(new File("D:\\Users\\Juweriah\\Documents\\Eclipse\\eclipse\\Test\\MNIST_Database_ARGB\\0_01.png")))));
		} catch (IOException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static Map<String,Double> getpercentage(BufferedImage bf){
		Map<String,Double> e = new HashMap<>();
		
		if(nn==null) {
			nn =  (NeuralNetwork) DataHandle.load();
			//nn = ds.nn;
			//onname = ds.onname;
		}
		/*try {
			ImageIO.write(Utils.toimage(Utils.to1d2image(bf,true),28), "png", new File("t.png"));
		} catch (IOException e1) {
			// XXX Auto-generated catch block
			e1.printStackTrace();
		}*/
		List<Double> ans = nn.predict(Utils.to1d2image(bf,true));
		//System.out.println(onname);
		//System.out.println(Utils.to1d2image(bf).length);
		//System.out.println(nn.x[0].length);
		int i = 0;
		for(String str:onname) {
			e.put(str, ans.get(i));
			i++;
		}	
		return e;
	
	}
}
class DataStorag implements Serializable {
	private static final long serialVersionUID = 5145325698916245514L;
	public List<String> onname = new ArrayList<>();// = {"0","1","2"};
	public NeuralNetwork nn;
}

class DataHandle {
	NN panel;

	public DataHandle(NN numberReco) {}

	public static File getdatafilename() {
		return new File("nn.dat");
		//return new File("Data.dat");
	}

	public static void save(Object ds) {
		try {
			ObjectOutputStream oos1 = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(getdatafilename())));
			oos1.writeObject(ds);
			oos1.close();
		} catch (NotSerializableException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object load() {
		try {
			ObjectInputStream oos = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(getdatafilename())));
			Object ob = oos.readObject();
			oos.close();
			return ob;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();

			return null;
		}
	}
}
