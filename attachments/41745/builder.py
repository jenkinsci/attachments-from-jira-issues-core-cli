#!/usr/bin/env python3
"""
Simple test builder.
"""
# core python packages
import argparse
import logging
import os
import signal
import time
# third party packages
# custom packages


class MyBuilder(object):
    logger_level = logging.DEBUG

    def __init__(self):
        self._init_logger()
        self._init_vars()
        self._init_cli()

    def _init_logger(self):
        name = self.__class__.__name__
        format_string = '[%(levelname)s]'
        format_string += ' %(name)s (%(lineno)d)'
        format_string += ' - %(relativeCreated)d  %(msecs)d '
        format_string += '- %(message)s'
        logging.basicConfig(format=format_string, level=self.logger_level)
        self.logger = logging.getLogger(name)
        self.logger.debug('Logger initialized: {}'.format(name))

    def _init_vars(self):
        pass

    def _init_cli(self):
        self.cli = argparse.ArgumentParser(description=__doc__)

    def parse_args(self):
        self.args = self.cli.parse_args()

    def __call__(self):
        self.logger.debug('Inside call.')
        self.parse_args()
        self.simple_build()

    def handle_second(self, signal_number, frame):
        self.logger.warn('Handling SECOND SIGTERM.')
        self.logger.warn('SECOND PID: {}'.format(os.getpid()))
        self.logger.warn('SECOND Signal number: {}'.format(signal_number))
        self.logger.warn('SECOND Frame: {}'.format(frame))
        self.logger.warn('SECOND Frame code: {}'.format(frame.f_code))


    def handle_sigterm(self, signal_number, frame):
        signal.signal(signal.SIGTERM, self.handle_second)
        self.logger.warn('Handling SIGTERM.')
        self.logger.warn('PID: {}'.format(os.getpid()))
        self.logger.warn('Signal number: {}'.format(signal_number))
        self.logger.warn('Frame: {}'.format(frame))
        self.logger.warn('Frame code: {}'.format(frame.f_code))
        #import pdb; pdb.set_trace()
        self.clean_up()
        raise SystemExit(99)

    def clean_up(self):
        self.logger.warn('Running clean up...')
        sleep_secs = 9
        for i in range(sleep_secs):
            self.logger.info('Clean sleep: {}'.format(i))
            time.sleep(1)

    def simple_build(self):
        signal.signal(signal.SIGTERM, self.handle_sigterm)
        my_pid = os.getpid()
        self.logger.info('my pid: {}'.format(my_pid))
        self.logger.info('process group: {}'.format(os.getpgid(my_pid)))
        sleep_time = 200
        self.logger.info('Sleeping for: {} secs'.format(sleep_time))
        time.sleep(sleep_time)


if __name__ == '__main__':
    mb = MyBuilder()
    mb()
