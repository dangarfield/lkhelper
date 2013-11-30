Object.distanceToHabitat = function (t, s, i, a) {
    var h = 0,
        u = 0,
        r = 0,
        o = 0,
        l = 0,
        n, e;
    i = parseInt(i, 10);
    a = parseInt(a, 10);
    t = parseInt(t, 10);
    s = parseInt(s, 10);
    u = s & 1 ? t + .5 : t;
    r = s;
    o = a & 1 ? i + .5 : i;
    l = a;
    n = Math.abs(o - u);
    e = Math.abs(l - r);
    return e * .5 >= n ? e : e * .5 + n;
};
