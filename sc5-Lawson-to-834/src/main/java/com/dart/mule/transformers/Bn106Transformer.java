package com.dart.mule.transformers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.config.i18n.CoreMessages;

import com.dart.utils.Bn106Cleaner;

/**
 * Bn106Transformer is a MuleSoft compliant transformer that can be invoked by a flow. 
 * It cleans up a Lanwson BN106 file to make it consumable by the flat file transformation
 * in Dataweave.
 * 
 * @author gillesramone
 *
 */
public class Bn106Transformer extends AbstractMessageTransformer{

	static final Logger logger = LogManager.getLogger(Bn106Transformer.class.getName());
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		
		logger.info("In Bn106Transformer transformMessage with " + message.getPayload().getClass().getCanonicalName());

		String cleanedUpContent = null;
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)message.getPayload()));
		
		Bn106Cleaner cleaner = new Bn106Cleaner(bufferedReader);
		try {
			
			StringBuffer stringBuffer = cleaner.clean();
			cleanedUpContent = stringBuffer.toString();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new TransformerException(CoreMessages.createStaticMessage("Unable to transform <"+ message.getPayload() +"> to a Bn106 compliant"), e);
			
		}
		
		dispose(message);
		
		return cleanedUpContent;
	}

	/**
	 * 
	 * @param message
	 */
	private void dispose(MuleMessage message) {

		logger.info("Disposing...");
		String originalFileName = message.getInboundProperty("originalFileName");
		String directory = message.getInboundProperty("directory");
		
		Path path = Paths.get(directory + File.separator + originalFileName);
		try {
			Files.delete(path);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	

}
