package io.github.cheergoivan.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * 
 * https://www.oschina.net/code/snippet_2005376_57297
 * 
 * https://github.com/centic9/jgit-cookbook
 * 
 * @author I321035
 *
 */
public class GitUtil {
	private static final PrintStream console=System.out;
	
	public static void commitAndPush(String localRepositoryPath, String message, String username, String password)
			throws Exception {
		commitAll(localRepositoryPath, message);
		push(localRepositoryPath, username, password);
	}

	public static boolean hasChanged(String localRepositoryPath) throws Exception {
		Git git = Git.open(new File(localRepositoryPath));
		Status status = git.status().call();
		return status.getMissing().size()!=0||status.getUntracked().size()!=0||status.getModified().size()!=0;
	}
	
	public static void addAllChanges(String localRepositoryPath) throws Exception{
		Git git = Git.open(new File(localRepositoryPath));
		Status status = git.status().call();
		for (String f : status.getMissing()) {
			System.out.println("Deleted:" + f);
			git.rm().addFilepattern(f).call();
		}
		for (String f : status.getUntracked()) {
			System.out.println("Added:" + f);
			git.add().addFilepattern(f).call();
		}
		for(String f:status.getModified()){
			System.out.println("Modified:" + f);
			git.add().addFilepattern(f).call();
		}
	}
	
	public static void commitAll(String localRepositoryPath, String message)
			throws Exception {
		Git git = Git.open(new File(localRepositoryPath));
		git.commit().setMessage(message).call();
	}

	public static void push(String localRepositoryPath, String username, String password) 
			throws Exception
			 {
		Git git = Git.open(new File(localRepositoryPath));
		System.out.println("Push to " + getOriginRemoteURL(git));
		PushCommand pushCommand = git.push();
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
		pushCommand.setCredentialsProvider(credentialsProvider);
		pushCommand.setForce(true).setPushAll();
		pushCommand.setProgressMonitor(new TextProgressMonitor());
		pushCommand.call();
		System.out.println();
		System.out.println("Push successfully!");
	}

	public static void cloneRepository(String url, String localRepositoryPath)
			throws Exception{
		System.out.println("Start downloading repository from remote repository "+url);
		CloneCommand cloneCommand = Git.cloneRepository().setProgressMonitor(new TextProgressMonitor()).setURI(url)
				.setDirectory(new File(localRepositoryPath));
		cloneCommand.call();
		System.out.println();
		System.out.println("Download repository successfully!");
	}

	public static void pullRepository(String localRepositoryPath) throws Exception {
		Git git = Git.open(new File(localRepositoryPath));
		System.out.println("Fetch From " + getOriginRemoteURL(git));
		PullCommand pullCommand = git.pull();
		pullCommand.setProgressMonitor(new TextProgressMonitor());
		PullResult pullResult = pullCommand.call();
		System.out.println("Merge Result:" + pullResult.getMergeResult().toString());
	}

	private static String getOriginRemoteURL(Git git) {
		return git.getRepository().getConfig().getString("remote", "origin", "url");
	}
	
	public static void disableOutputMessage(){
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
	}
	
	public static void enableOutputMessage(){
		System.setOut(console);
	}
	
}
