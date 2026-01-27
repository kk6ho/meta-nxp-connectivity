SUMMARY = "EasyMesh"
DESCRIPTION = "EasyMesh data element binaries, libraries and configuration files"
LICENSE = "Proprietary"

COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:armv8 = "(.*)"
COMPATIBLE_MACHINE:armv8a = "(.*)"


SRC_URI[md5sum] = "45a184db4eaa3b2427c41535b1b44a4e"
SRC_URI[sha256sum] = "19e4900c3bdc6da3f10caab66012ece33aacd9d0a073e77332d11e9561983b31"

inherit fsl-eula2-unpack2
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

#TODO: switch to NXP external url
SRC_URI = "http://10.12.176.13/files/easymesh-yocto-1.0-M012.bin;fsl-eula=true"
S = "${UNPACKDIR}/easymesh-yocto-1.0-M012"

INSANE_SKIP:${PN} = "already-stripped ldflags"
INSANE_SKIP:${PN}-dev = "ldflags"
INSANE_SKIP:${PN} += "file-rdeps"

RDEPENDS:${PN} += " ebtables libpcap "

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/bin/easymesh ${D}${bindir}/
    install -m 0755 ${S}/bin/easymesh_cli ${D}${bindir}/
    install -m 0755 ${S}/bin/hle_entity ${D}${bindir}/

    install -d ${D}${sysconfdir}/easymesh/de-yang
    
    for script in ${S}/etc/easymesh/*.sh; do
        [ -f "$script" ] && install -m 0755 $script ${D}${sysconfdir}/easymesh/
    done
    
    for config in ${S}/etc/easymesh/*.config; do
        [ -f "$config" ] && install -m 0644 $config ${D}${sysconfdir}/easymesh/
    done
    
    if [ -f ${S}/etc/easymesh/note.txt ]; then
        install -m 0644 ${S}/etc/easymesh/note.txt ${D}${sysconfdir}/easymesh/
    fi

    for yang in ${S}/etc/easymesh/de-yang/*.yang; do
        [ -f "$yang" ] && install -m 0644 $yang ${D}${sysconfdir}/easymesh/de-yang/
    done

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/usr/lib/systemd/system/easymesh-agent.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${S}/usr/lib/systemd/system/easymesh-controller.service ${D}${systemd_system_unitdir}/

    install -d ${D}${libdir}
    install -m 0755 ${S}/lib/libyang.so.2.41.0 ${D}${libdir}/
    
    cd ${D}${libdir}
    ln -sf libyang.so.2.41.0 libyang.so.2
    ln -sf libyang.so.2 libyang.so
}

FILES:${PN} += " \
    ${bindir}/easymesh \
    ${bindir}/easymesh_cli \
    ${bindir}/hle_entity \
    ${sysconfdir}/easymesh/* \
    ${systemd_system_unitdir}/*.service \
    ${libdir}/easymesh \
    ${libdir}/libyang.so* \
"

FILES:${PN}-dev += ""

PROVIDES += "libyang"

RDEPENDS:${PN} += "bash"

