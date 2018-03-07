package com.bmtc.svn.common.utils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.SFTPv3FileHandle;
import ch.ethz.ssh2.Session;

public class SFTPUtil {

	private final static Logger log = Logger.getLogger(SFTPUtil.class);
	
	private static final String HOSTNAME = "192.168.140.133";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "rootroot";
	
	public static void main(String[] args) {
		try {
			/* Create a connection instance */

			Connection conn = new Connection(HOSTNAME);

			/* Now connect */

			conn.connect();

			/* Authenticate */

			boolean isAuthenticated = conn.authenticateWithPassword(USERNAME, PASSWORD);

			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}

			/* Create a session */

			Session sess = conn.openSession();

//			sess.execCommand("ssh 192.168.140.134 ls /home");
			sess.execCommand("scp /home/temp/temp.txt 192.168.140.134:/home/temp");//复制文件，互信节点之间不需要用户名和密码

			/*
			 * Advanced:
			 * The following is a demo on how one can read from stdout and
			 * stderr without having to use two parallel worker threads (i.e.,
			 * we don't use the Streamgobblers here) and at the same time not
			 * risking a deadlock (due to a filled SSH2 channel window, caused
			 * by the stream which you are currently NOT reading from =).
			 */

			/* Don't wrap these streams and don't let other threads work on
			 * these streams while you work with Session.waitForCondition()!!!
			 */

			InputStream stdout = sess.getStdout();
			InputStream stderr = sess.getStderr();

			byte[] buffer = new byte[8192];

			while (true) {
				if ((stdout.available() == 0) && (stderr.available() == 0)) {
					/* Even though currently there is no data available, it may be that new data arrives
					 * and the session's underlying channel is closed before we call waitForCondition().
					 * This means that EOF and STDOUT_DATA (or STDERR_DATA, or both) may
					 * be set together.
					 */

					int conditions = sess.waitForCondition(ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA
							| ChannelCondition.EOF, 2000);

					/* Wait no longer than 2 seconds (= 2000 milliseconds) */

					if ((conditions & ChannelCondition.TIMEOUT) != 0) {
						/* A timeout occured. */
						throw new IOException("Timeout while waiting for data from peer.");
					}

					/* Here we do not need to check separately for CLOSED, since CLOSED implies EOF */

					if ((conditions & ChannelCondition.EOF) != 0) {
						/* The remote side won't send us further data... */

						if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0) {
							/* ... and we have consumed all data in the local arrival window. */
							break;
						}
					}

					/* OK, either STDOUT_DATA or STDERR_DATA (or both) is set. */

					// You can be paranoid and check that the library is not going nuts:
					// if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0)
					//	throw new IllegalStateException("Unexpected condition result (" + conditions + ")");
				}

				/* If you below replace "while" with "if", then the way the output appears on the local
				 * stdout and stder streams is more "balanced". Addtionally reducing the buffer size
				 * will also improve the interleaving, but performance will slightly suffer.
				 * OKOK, that all matters only if you get HUGE amounts of stdout and stderr data =)
				 */

				while (stdout.available() > 0) {
					int len = stdout.read(buffer);
					if (len > 0) {// this check is somewhat paranoid
						System.out.write(buffer, 0, len);
					}
				}

				while (stderr.available() > 0) {
					int len = stderr.read(buffer);
					if (len > 0) {// this check is somewhat paranoid
						System.err.write(buffer, 0, len);
					}
				}
			}

//			sess.execCommand(" ls /home");
			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
	
	
	public static void main1(String[] args) {
		String fileName = "/home/temp/temp.conf";
		SFTPUtil sftp = new SFTPUtil();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 100; i++) {
			buffer = buffer.append(i+ "abc=222222222222222222222222222222222222222222\n");
		}
		String data = buffer.toString();
		try {
			sftp.writeFile(fileName, data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] bytes;
		try {
			bytes = sftp.readFile(fileName);
			String str = new String(bytes);
			System.out.println("str\n" + str);
			System.out.println("---------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// constructors

	public void createDirectory(String directory) throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			client.mkdir(directory, 0700);

			log.debug("Created Directory: " + directory);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	public boolean exists(String path) {
		try {
			getAttributes(path);

			return true;
		} catch (IOException ioErr) {
			return false;
		}
	}

	public Date getMTime(String fileName) throws IOException {
		return new Date(getAttributes(fileName).mtime * 1000L);
	}

	public long getSize(String fileName) throws IOException {
		return getAttributes(fileName).size;
	}

	public boolean isDirectory(String fileName) throws IOException {
		return getAttributes(fileName).isDirectory();
	}

	
	public List<String> listFiles(String directory) throws IOException {
		List<SFTPv3DirectoryEntry> files = getFileList(directory);
		List<String> names = new ArrayList<String>();

		for (Iterator<SFTPv3DirectoryEntry> elements = files.iterator(); elements
				.hasNext();) {
			names.add(elements.next().filename.trim());
		}

		return names;
	}

	public Map<String, List<String>> listFilesByExtension(String directory)
			throws IOException {
		List<SFTPv3DirectoryEntry> files = getFileList(directory);
		Map<String, List<String>> fileNamesMap = new TreeMap<String, List<String>>();

		for (Iterator<SFTPv3DirectoryEntry> elements = files.iterator(); elements
				.hasNext();) {
			String name = elements.next().filename.trim();
			int dotChar = name.lastIndexOf('.');

			if (dotChar == -1) {
				continue;
			}

			String extension = name.substring(dotChar + 1);
			List<String> fileList = fileNamesMap.get(extension);

			if (fileList == null) {
				fileList = new ArrayList<String>();

				fileNamesMap.put(extension, fileList);
			}

			fileList.add(name);
		}

		return fileNamesMap;
	}

	public List<String> listSubdirectories(String directory) throws IOException {
		List<SFTPv3DirectoryEntry> files = getFileList(directory);
		List<String> names = new ArrayList<String>();

		for (Iterator<SFTPv3DirectoryEntry> elements = files.iterator(); elements
				.hasNext();) {
			SFTPv3DirectoryEntry file = elements.next();

			if (file.attributes.isDirectory()) {
				names.add(file.filename.trim());
			}
		}

		return names;
	}

	public void moveDirectory(String directoryName, String newDirectoryName)
			throws IOException {
		moveFile(directoryName, newDirectoryName);
	}

	public void moveFile(String fileName, String newFileName)
			throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			log.debug("Moving " + fileName + " to " + newFileName);

			client.mv(fileName, newFileName);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	public byte[] readFile(String fileName) throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			log.debug("Reading from file " + fileName );

			return readRemoteFile(client, fileName);
		} finally {
			if (client != null) {
				client.close();
			}
			
			conn.close();
		}
	}

	public Map<String, byte[]> readFilesWithExtension(String directory,
			String fileExtension) throws IOException {
		String fileSeparator = System.getProperty("file.separator");

		if (!directory.endsWith(fileSeparator)) {
			directory += fileSeparator;
		}

		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			Map<String, byte[]> fileContents = new TreeMap<String, byte[]>();
			List<SFTPv3DirectoryEntry> files = getFileList(client, directory);

			log.debug("Reading files with extension " + fileExtension
					+ " from directory " + directory);

			for (Iterator<SFTPv3DirectoryEntry> elements = files.iterator(); elements
					.hasNext();) {
				SFTPv3DirectoryEntry file = (SFTPv3DirectoryEntry) elements
						.next();

				// Process entry, so long as it's not a subdir
				if (file.attributes.isRegularFile()) {
					String fileName = file.filename.trim();

					if (!fileExtension.equals("*")) {
						int dotChar = fileName.lastIndexOf('.');

						// skip files that don't have "." in the filename
						if (dotChar == -1) {
							continue;
						}

						String extension = fileName.substring(dotChar + 1);

						// skip file if part after the "." doesn't match fileExtension
						if (!fileExtension.equals(extension)) {
							continue;
						}
					}

					byte[] content = readRemoteFile(client, directory
							+ fileName);

					fileContents.put(fileName, content);
				}
			}

			log.debug("Read " + fileContents.size()
					+ " files with extension " + fileExtension
					+ " from directory " + directory);

			return fileContents;
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	/*
	 * I'm commenting this out because it doesn't appear to be used and I didn't
	 * update it to handle "*"
	 * @SuppressWarnings("unchecked") public Map<String, Map<String, byte[]>>
	 * readFilesWithExtensions(String directory, Set<String> exts) { if
	 * (directory == null) throw new
	 * NullPointerException("Directory cannot be null!"); if (exts == null)
	 * throw new NullPointerException("extensions cannot be null!"); Map<String,
	 * Map<String, byte[]>> extMap = new HashMap<String, Map<String, byte[]>>();
	 * if (log.isDebugEnabled()) log.debug("Reading files with extension '" +
	 * StringUtils.join(exts, "' '") + "' from directory " + directory + " via "
	 * + host); SFTPv3Client client = getSFTPClient(); try {
	 * Vector<SFTPv3DirectoryEntry> dir = client.ls(directory);
	 * for(SFTPv3DirectoryEntry file : dir) { String fileName = file.filename;
	 * if(".".equals(fileName.trim()) == false && "..".equals(fileName.trim())
	 * == false && file.attributes.isRegularFile()) { //if
	 * (log.isDebugEnabled()) log.debug("Evaluating file: " + fileName); int
	 * beginIndex = fileName.lastIndexOf('.') + 1; if (beginIndex == 0)
	 * continue; String fileExt = fileName.substring(beginIndex); //if
	 * (log.isDebugEnabled()) log.debug("file: " + fileName +
	 * " has extension: '" + fileExt + "'"); if (exts.contains(fileExt) ==
	 * false) continue; //if (log.isDebugEnabled()) log.debug("file extension "
	 * + fileExt + " is in set - file will be retrieved."); byte[] content =
	 * readRemoteFile(client, directory + fileName); if
	 * (extMap.containsKey(fileExt) == false) extMap.put(fileExt, new
	 * HashMap<String, byte[]>()); Map<String, byte[]> fileContents =
	 * extMap.get(fileExt); fileContents.put(fileName, content);
	 * extMap.put(fileExt, fileContents); //if(log.isDebugEnabled())
	 * log.debug("Retrieved " + directory + fileName); } } //end for each
	 * directory entry } catch (IOException e) { throw new
	 * RuntimeException("Could not remove directory " + directory + " via " +
	 * host, e); } finally { client.close(); disconnect(); } return extMap; }
	 */

	public void removeDirectory(String directoryPath, boolean deleteContent)
			throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			removeDirectoryRecursive(directoryPath, deleteContent, client);

			log.debug("Removed " + directoryPath);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	public void removeFile(String fileName) throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			client.rm(fileName);

			log.debug("Removed " + fileName);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	public void writeFile(String fileName, String content) throws IOException {
		writeFile(fileName, content.getBytes());
	}

	public void writeFile(String fileName, byte[] content) throws IOException {
		writeFile(fileName, new ByteArrayInputStream(content));
	}

	public void writeFile(String fileName, File file) throws IOException {
		writeFile(fileName, new BufferedInputStream(new FileInputStream(file)));
	}

	// private methods
	private Connection getConnection() throws IOException {
		
		Connection conn = new Connection(HOSTNAME);

		/* Now connect */

		conn.connect();

		/*
		 * Authenticate. If you get an IOException saying something like
		 * "Authentication method password not supported by the server at this stage."
		 * then please check the FAQ.
		 */

		boolean isAuthenticated = conn.authenticateWithPassword(USERNAME,PASSWORD);
		if (isAuthenticated == false) {
			throw new IOException("Authentication failed.");
		}
		
		return conn;
	}

	private SFTPv3FileAttributes getAttributes(String fileName)
			throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			return client.stat(fileName);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	private List<SFTPv3DirectoryEntry> getFileList(String directory)
			throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			return getFileList(client, directory);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	private List<SFTPv3DirectoryEntry> getFileList(SFTPv3Client client,
			String directory) throws IOException {
		List<SFTPv3DirectoryEntry> fileEntries = new ArrayList<SFTPv3DirectoryEntry>();
		Vector<?> files = (Vector<?>) client.ls(directory);

		for (Iterator<?> elements = files.iterator(); elements.hasNext();) {
			SFTPv3DirectoryEntry file = (SFTPv3DirectoryEntry) elements.next();
			String fileName = file.filename.trim();

			if (!fileName.equals(".") && !fileName.equals("..")) {
				fileEntries.add(file);
			}
		}

		return fileEntries;
	}

	private byte[] readRemoteFile(SFTPv3Client client, String fileName)
			throws IOException {
		ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
		SFTPv3FileHandle handle = client.openFileRO(fileName);

		try {
			byte[] readBuffer = new byte[8192];
			long offset = 0;
			int bytesRead;

			while ((bytesRead = client.read(handle, offset, readBuffer, 0,
					readBuffer.length)) >= 0) {
				outBytes.write(readBuffer, 0, bytesRead);

				offset += bytesRead;
			}
		} finally {
			client.closeFile(handle);
		}

		log.debug(fileName + ": read complete");

		return outBytes.toByteArray();
	}

	private void removeDirectoryRecursive(String directory,
			boolean deleteContent, SFTPv3Client client) throws IOException {
		List<SFTPv3DirectoryEntry> files = getFileList(client, directory);

		if (deleteContent == false) {
			if (files.size() > 0) {
				log.error("Directory is not empty: ");
				for (SFTPv3DirectoryEntry file : files) {
					log.error(file.filename);
				}
				throw new RuntimeException("Directory is not empty");
			}
			// we fall thru since we've determined that the directory is empty.
		}
		for (SFTPv3DirectoryEntry file : files) {
			String fileName = file.filename;
			if (".".equals(fileName.trim()) == false
					&& "..".equals(fileName.trim()) == false) {
				// tl: added this to make this method recursive
				if (file.attributes.isDirectory()) {
					removeDirectoryRecursive(directory + "/" + fileName,
							deleteContent, client);
				} else {
					// tl: end  added this to make this method recursive
					client.rm(directory + "/" + fileName);
				}
			}
		}
		client.rmdir(directory);
	}

	private void writeFile(String fileName, InputStream inStream)
			throws IOException {
		Connection conn = getConnection();
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			writeFile(client, fileName, inStream);
		} finally {
			if (client != null) {
				client.close();
			}

			conn.close();
		}
	}

	private void writeFile(SFTPv3Client client, String fileName,
			InputStream inStream) throws IOException {
		SFTPv3FileHandle handle = client.createFile(fileName);

		try {
			byte[] readBuffer = new byte[8192];
			long offset = 0;
			int bytesRead;

			while ((bytesRead = inStream.read(readBuffer, 0, readBuffer.length)) >= 0) {
				client.write(handle, offset, readBuffer, 0, bytesRead);

				offset += bytesRead;
			}
		} finally {
			client.closeFile(handle);
		}
	}

}
