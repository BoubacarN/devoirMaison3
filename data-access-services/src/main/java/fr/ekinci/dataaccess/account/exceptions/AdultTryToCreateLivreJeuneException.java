package fr.ekinci.dataaccess.account.exceptions;

/**
 * @author Gokan EKINCI
 */
public class AdultTryToCreateLivreJeuneException extends RuntimeException {
	public AdultTryToCreateLivreJeuneException() {
		super("More than 18 years old cannot create Livret Jeune");
	}
}
