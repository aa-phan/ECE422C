Documentation on how the test cases work:

IsSorted Test Cases:
	- Test 1: Check for return value = true on a sorted array
	- Test 2: Check for return value = false on unsorted array
	- Test 3: Check for return value = false on n = 0
	
find Test Cases:
	- Test 1: Check for a return value != -1 where the target exists within the first n elements of the array
	- Test 2: Check for return value == -1 where the target doesn't exist within the first n elements of the array
	- Test 3: Check for return value == -1 where the target exists outside of the first n elements of the array
	- Test 4: Check for return value == -1 where the target doesn't exist anywhere in the array
	- Test 5: Check for return value != -1 on a very large array
	
copyAndInsert Test Cases:
	- Test 1: Check for a returned array where the original array doesn't include the target, and the returned array does. 
	- Test 2: Check for returned array where the original array contains the target and is returned as is
	- Test 3: Check for returned array where n = original_array.length and target pre-exists
	- Test 4: Check for returned array where the array has random numbers, very large and positive/negative, and the target doesn't pre-exist
	> Ensure the returned arrays are sorted and the original array is unmodified
	
insertInPlace Test Cases:
	- Test 1: Check for return of n when the first n elements already contains the target
	- Test 2: Check for return of n + 1 when the first n elements don't contain the target
	- Test 3: Check for return of n where n = original_array.length and target already exists
	- Test 4: Check for return of n + 1 where the array has random numbers, very large and positive/negative, and target doesn't pre-exist
	> Ensure the original array is sorted and contains the new element in its proper position
	
insertSort Test Cases:
	- Test 1: Check for isSorted = True after an unsorted array is sorted
	- Test 2: Check for isSorted = True on an already sorted array
	- Test 3: Check for isSorted = True on n = 1 and an unsorted array
	- Test 4: Check for isSorted = True on a very large array
	