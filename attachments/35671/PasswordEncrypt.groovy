import com.trilead.ssh2.crypto.Base64;
import javax.crypto.Cipher;
import jenkins.security.CryptoConfidentialKey;
import hudson.util.Secret;

CryptoConfidentialKey KEY = new CryptoConfidentialKey(Secret.class.getName());
Cipher cipher = KEY.encrypt();
String MAGIC = "::::MAGIC::::";

String VALUE_TO_ENCRYPT = args[0];
println(new String(Base64.encode(cipher.doFinal((VALUE_TO_ENCRYPT + MAGIC).getBytes("UTF-8")))));
