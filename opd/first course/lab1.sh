cd ~
mkdir lab0 
cd lab0 
mkdir donphan4  
mkdir golett5 
mkdir kadabra6 
cd donphan4 
touch ferroseed 
echo Живет Cave Mountain > ferroseed 
touch gastly
echo Возможности Sky=8 Power=1 Intillegence3 > gastly
echo Invisibility=0 Phasing=0 >> gastly
touch electrode
echo satk=8 sdef=8 > electrode
echo spd=14 >> electrode
cd .. 
touch dratini0 
echo Тип диеты > dratini0
echo Omnivore >> dratini0
touch electrode8 
echo weight=146.8 height=47.0 atk=5 > electrode8 
echo def=7 >> electrode8 
cd golett5 
touch gulpin 
echo Развитые способности Gluttony > gulpin 
touch garchomp 
echo Возможности > garchomp
echo Overland=8 Surface=8 Sky=14 Burrow=8 Jump=3 Power=5 >> garchomp 
echo Intelligence=4 >> garchomp  
mkdir wigglytuff 
mkdir lampent 
touch mandibuzz
touch trubbish 
echo Живет Desert Mountain > mandibuzz
echo weight=68.3 > trubbish 
echo height=24.0 atk=5 def=6 >> trubbish 
cd ..
touch jellicent8 
echo weight=297.6 height=87.0 atk=6 > jellicent8 
echo def=7 >> jellicent8
cd kadabra6 
touch zweilous 
echo Развитые способности Prankster > zweilous 
mkdir klang 
mkdir dugtrio 
cd ~
cd lab0
chmod 577 donphan4 
cd donphan4 
chmod 600 ferroseed 
chmod 006 electrode 
chmod 400 gastly 
cd ..
chmod 440 dratini0 
chmod 604 electrode8 
chmod 700 golett5 
cd golett5 
chmod 440 gulpin 
chmod 060 garchomp 
chmod 753 wigglytuff 
chmod 512 lampent 
chmod 440 mandibuzz 
chmod 640 trubbish 
cd .. 
chmod 640 jellicent8 
chmod 335 kadabra6 
cd kadabra6
chmod 622 zweilous 
chmod 307 klang 
chmod 335 dugtrio
cd ~
cd lab0
chmod 777 kadabra6
cd kadabra6
chmod 777 dugtrio
chmod 777 klang
cd ..
cd golett5
chmod 777 lampent
cd ~
cd lab0
cp -r kadabra6 golett5/lampent/
chmod 335 kadabra6
cd kadabra6
chmod 335 dugtrio
chmod 307 klang 
cd ..
cd golett5
chmod 512 lampent
cd ~
cd lab0
chmod 777 donphan4
cd donphan4
cp /home/studs/s408586/lab0/dratini0 electrodedratini
cd ..
ln -s /home/studs/s408586/lab0/donphan4 Copy_28
chmod 577 donphan4
cd golett5
ln -s jellicent8 mandibuzzjellicent
ln /home/studs/s408586/lab0/electrode8 gulpinelectrode
cd ~
cd lab0
cd donphan4
chmod 777 electrode
cat ferroseed electrode > /home/studs/s408586/lab0/electrode8_70
chmod 006 electrode 
cd ~
cd lab0
cp dratini0 /home/studs/s408586/lab0/kadabra6/dugtrio
cd ~
cd lab0
wc -m k* */k* */*/k* | sort -k1 2>&1
ls -lR | grep ^- | grep -h "le" | sort -n | tail -2 | 2>/tmp/lab0_errors
grep -v "n$" | cat -n $(ls -dp * | grep -v "/$")
ls -lt | grep "8$"
ls -l | grep -h "de" | sort -k6 | 2> /tmp/lab0_errors
grep -v "/$" | ls -l | cat d* | sort -k1
cd ~
cd lab0
chmod 777 dratini0
rm dratini0
chmod 777 kadabra6
cd kadabra6
rm zweilous
cd ..
cd golett5
rm mandibuzzjellicent
rm gulpinelectrode
cd ..
cd kadabra6
chmod -R 777 .
cd ..
rm -rf kadabra6
cd golett5
cd lampent
chmod -R 777 .
cd ..
rm -rf lampent
cd ~
echo 'end!' 
