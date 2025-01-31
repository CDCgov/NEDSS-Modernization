package gov.cdc.nedss.geocoding.util;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class tracks the state of a multiple-match evaluation.
 * 
 * @author rdodge
 *
 */
public class MultiMatchState {

	// Constants //
	public static Float zeroDiff = new Float(0.0f);
	public static Integer zeroCount = new Integer(0);


	// Members //

	/** Groups/orders clusters of indexes by score. */
	private SortedMap<Object, Object> mapByScore = new TreeMap<Object, Object>();
	private SortedMap<Object, Object> nullScoreMap = new TreeMap<Object, Object>();

	private float matchTieThreshold = 0.0f;

	private Float nextScoreDiff = null;
	private Integer nextScoreCount = null;

	private boolean mustRecalculate = false;

	/**
	 * Adds an index to the map.
	 * 
	 * @param index
	 * @param score
	 */
	public void addIndex(int index, Float score) {

		mustRecalculate = true;

		Integer indexItem = new Integer(index);
		if (score != null) {
			if (!mapByScore.containsKey(score)) {
				mapByScore.put(score, new TreeMap<Object, Object>());
			}
			((SortedMap<Object, Object>) mapByScore.get(score)).put(indexItem, indexItem);
		}
		else {
			nullScoreMap.put(indexItem, indexItem);
		}
	}

	/** Clears all state; prepares the object for reuse. */ 
	public void clear() {

		mapByScore.clear();
		nullScoreMap.clear();

		matchTieThreshold = 0.0f;
		nextScoreDiff = null;
		nextScoreCount = null;

		mustRecalculate = false;
	}

	/** Returns match tie threshold configured for this object.  @return */
	public float getMatchTieThreshold() {
		return matchTieThreshold;
	}

	/** Sets the match tie threshold to the indicated value. */
	public void setMatchTieThreshold(float matchTieThreshold) {
		mustRecalculate = true;
		this.matchTieThreshold = matchTieThreshold;
	}


	/** Returns the number of distinct (non-null) scores.  @return */
	public int getNumScores() {
		return mapByScore.size();
	}

	/** Returns the total number of matches that return a score.  @return */
	public int getNumScoreElements() {
		int count = 0;
		for (Iterator<Object> it = mapByScore.values().iterator(); it.hasNext(); ) {
			count += ((SortedMap<?,?>) it.next()).size();
		}
		return count;
	}

	/** Returns the total number of matches that do not return a score.  @return */
	public int getNumNullElements() {
		return nullScoreMap.size();
	}

	/** Returns the total number of matches.  @return */
	public int getNumElements() {
		return getNumScoreElements() + getNumNullElements();
	}


	/** Returns the best score (if any).  @return */
	public Float getBestScore() {
		return (Float) mapByScore.firstKey();
	}

	/** Returns the next best score (if any).  @return */
	public Float getNextBestScore() {
		Float score = null;
		Iterator<Object> it = mapByScore.keySet().iterator();
		if (it.hasNext()) {
			it.next();  // skip 1st item
		}
		if (it.hasNext()) {
			score = (Float) it.next();  // score <= 2nd item
		}
		return score;
	}

	/**
	 * Returns the number of matches with the indicated score.
	 * 
	 * @param score
	 * @return
	 */
	public int getNumElementsWithScore(Float score) {
		int count = 0;
		if (score != null) {
			if (mapByScore.containsKey(score)) {
				count = ((SortedMap<?,?>) mapByScore.get(score)).size();
			}
		}
		else {
			count = nullScoreMap.size();
		}
		return count;
	}

	/**
	 * Returns the first match returned with the indicated score.
	 * 
	 * @param score
	 * @return
	 */
	public Integer getFirstIndexWithScore(Float score) {
		Integer index = null;
		if (score != null) {
			if (mapByScore.containsKey(score)) {
				index = (Integer) ((SortedMap<?,?>) mapByScore.get(score)).firstKey();
			}
		}
		else {
			index = (Integer) nullScoreMap.firstKey();
		}
		return index;
	}

	/**
	 * Returns the last match returned with the indicated score.
	 * 
	 * @param score
	 * @return
	 */
	public Integer getLastIndexWithScore(Float score) {
		Integer index = null;
		if (score != null) {
			if (mapByScore.containsKey(score)) {
				index = (Integer) ((SortedMap<?,?>) mapByScore.get(score)).lastKey();
			}
		}
		else {
			index = (Integer) nullScoreMap.lastKey();
		}
		return index;
	}

	/** Returns the number of matches with the best score.  @return */
	public int getNumElementsWithBestScore() {
		Float bestScore = getBestScore();
		return bestScore != null ? getNumElementsWithScore(bestScore) : 0;
	}

	/** Returns the number of matches within the match tie threshold.  @return */
	public int getNumElementsWithinMatchTieThreshold() {

		// Shortcut for regular threshold //
		if (matchTieThreshold <= 0.0f) {
			return getNumElementsWithBestScore();  // EARLY EXIT
		}

		// Positive threshold: Count items within threshold //
		int count = 0;
		Float bestScore = this.getBestScore();
		if (bestScore != null) {

			// Count must be >= 1.  We will recount it below //
			float bestScoreValue = bestScore.floatValue();
			Float score = null;

			for (Iterator<Object> it = mapByScore.keySet().iterator(); it.hasNext(); ) {
				score = (Float) it.next();
				if (score.floatValue() - bestScoreValue <= matchTieThreshold) {
					count += getNumElementsWithScore(score);
				}
				else {
					break;  // LOOP EXIT - rest of ordered list will contain only out-of-range values
				}
			}
		}
		return count;
	}

	/** Returns the first match returned with the best score.  @return */
	public Integer getFirstIndexWithBestScore() {
		Float bestScore = getBestScore();
		return bestScore != null ? getFirstIndexWithScore(bestScore) : null;
	}

	/** Returns the first match returned within the match tie threshold.  @return */
	public Integer getFirstIndexWithinMatchTieThreshold() {
		
		// Shortcut for regular threshold //
		if (matchTieThreshold <= 0.0f) {
			return getFirstIndexWithBestScore();  // EARLY EXIT
		}

		// Positive threshold: Find smallest index of the items within threshold //
		Integer index = null;
		Float bestScore = this.getBestScore();
		if (bestScore != null) {
			
			// Count must be >= 1.  We are guaranteed a result //
			float bestScoreValue = bestScore.floatValue();
			Float score = null;

			for (Iterator<Object> it = mapByScore.keySet().iterator(); it.hasNext(); ) {
				score = (Float) it.next();
				if (score.floatValue() - bestScoreValue <= matchTieThreshold) {
					Integer currentIndex = this.getFirstIndexWithScore(score);
					index = index == null || currentIndex.intValue() < index.intValue() ? currentIndex : index;
				}
				else {
					break;  // LOOP EXIT - rest of ordered list will contain only out-of-range values
				}
			}
		}
		return index;
	}

	/** Returns the last match returned with the best score.  @return */
	public Integer getLastIndexWithBestScore() {
		Float bestScore = getBestScore();
		return bestScore != null ? getLastIndexWithScore(bestScore) : null;
	}

	/** Returns the last match returned within the match tie threshold.  @return */
	public Integer getLastIndexWithinMatchTieThreshold() {
		
		// Shortcut for regular threshold //
		if (matchTieThreshold <= 0.0f) {
			return getLastIndexWithBestScore();  // EARLY EXIT
		}

		// Positive threshold: Find largest index of the items within threshold //
		Integer index = null;
		Float bestScore = this.getBestScore();
		if (bestScore != null) {
			
			// Count must be >= 1.  We are guaranteed a result //
			float bestScoreValue = bestScore.floatValue();
			Float score = null;

			for (Iterator<Object> it = mapByScore.keySet().iterator(); it.hasNext(); ) {
				score = (Float) it.next();
				if (score.floatValue() - bestScoreValue <= matchTieThreshold) {
					Integer currentIndex = this.getLastIndexWithScore(score);
					index = index == null || currentIndex.intValue() > index.intValue() ? currentIndex : index;
				}
				else {
					break;  // LOOP EXIT - rest of ordered list will contain only values out of range
				}
			}
		}
		return index;
	}

	/** Returns the number of matches with the next best score.  @return */
	public int getNumElementsWithNextBestScore() {
		Float nextBestScore = getNextBestScore();
		return nextBestScore != null ? getNumElementsWithScore(nextBestScore) : 0;
	}

	/** Returns the first match returned with the next best score.  @return */
	public Integer getFirstIndexWithNextBestScore() {
		Float nextBestScore = getNextBestScore();
		return nextBestScore != null ? getFirstIndexWithScore(nextBestScore) : null;
	}

	/** Returns the last match returned with the next best score.  @return */
	public Integer getLastIndexWithNextBestScore() {
		Float nextBestScore = getNextBestScore();
		return nextBestScore != null ? getLastIndexWithScore(nextBestScore) : null;
	}


	/** Returns the difference between the best and next best score, if available, <code>null</code> otherwise.  @return */
	public Float getNextScoreDiff() {
		if (mustRecalculate) {
			recalculate();
		}
		return nextScoreDiff;
	}

	/** Returns the number of matches with the next best score, if available, <code>null</code> otherwise.  @return */
	public Integer getNextScoreCount() {
		if (mustRecalculate) {
			recalculate();
		}
		return nextScoreCount;
	}

	/** Recalculates status members. */
	private void recalculate() {

		// Clear out fields //
		nextScoreDiff = null;
		nextScoreCount = null;

		// Determine case based on the # matches with best score //
		int bestScoreCount = this.getNumElementsWithinMatchTieThreshold();
		if (bestScoreCount > 1) {

			// Tie: Diff is zero, Count is the # within the threshold minus one.
			nextScoreDiff = zeroDiff;
			nextScoreCount = new Integer(bestScoreCount - 1);
		}
		else if (bestScoreCount == 1) {

			// No tie: Diff is next best score minus best score, Count is the # tied
			// for second place.  Note that match threshold is NOT applied here.
			//
			// FYI: It is acceptable for the "get...NextBest...()" methods to ignore
			// a positive match tie threshold because they are only called when
			// the "best score" count is 1, and reducing the threshold to zero in that
			// case would have no effect on the result.  If a positive threshold lumps
			// in any additional records, then the "best score" count must necessarily
			// be greater than 1 (and thus the following case would not even be entered).
			//
			int nextBestScoreCount = getNumElementsWithNextBestScore();
			if (nextBestScoreCount > 0) {
				nextScoreDiff = new Float(getNextBestScore().floatValue() - getBestScore().floatValue());
				nextScoreCount = new Integer(getNumElementsWithNextBestScore());
			}
			else {
				nextScoreDiff = null;
				nextScoreCount = zeroCount;
			}
		}
		else {  // (bestScoreCount == 0) - either no rows added or all have score == null

			// Note: As long as this class is used only in multi-match situations, this case
			// will not occur (unless the geodata client returns multiple zip-level matches
			// based on one zip code - unlikely).  This block will also support the single,
			// zip-level match case.
			nextScoreDiff = null;
			nextScoreCount = zeroCount;
		}

		mustRecalculate = false;
	}
}
