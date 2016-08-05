BEGIN {
	FS = "->"
}
{
	split($2, a, " ")
	for (x in a) {
		w = a[x]
		# print w
		iidx[w] = iidx[w] $1
	}
}
END {
	for(w in iidx) {
		print w " -> " iidx[w]
	}
}
